package com.yx.admin.server.controller.sys;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.PageModel;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.MessageCenterEnum;
import com.yx.sys.common.MessageCenterRankingEnum;
import com.yx.sys.common.MessageCenterStatusEnum;
import com.yx.sys.common.constant.CacheKeyContstant;
import com.yx.sys.model.SysMessageCenter;
import com.yx.sys.rpc.api.SysMessageCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 消息中心表前端控制器
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
@Slf4j
@RestController
@RequestMapping("/sysMessageCenter")
@Api(value = "消息中心", description = "消息中心")
public class SysMessageCenterController extends BaseController {


    @Autowired
    private SysMessageCenterService sysMessageCenterService;

    /**
     * 根据消息中心表ID查询
     *
     * @param id 消息中心表ID
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "根据消息中心表ID查询")
    @GetMapping("/select/{id}")
    @RequiresPermissions("sys:sysMessageCenter:read")
    @SysLogOpt(module = "根据消息中心表ID查询", value = "根据消息中心表ID查询", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectById(@PathVariable Long id) {
        Assert.notNull(id);
        SysMessageCenter entity = sysMessageCenterService.selectById(id);
        return ResultUtil.ok(entity);
    }

    /**
     * 查询消息中心表分页方法
     *
     * @param pageModel 分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "查询消息中心表分页方法")
    @PostMapping("/selectPage")
    @RequiresPermissions("sys:sysMessageCenter:read")
    @SysLogOpt(module = "查询消息中心表分页方法", value = "查询消息中心表分页方法", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectPage(@RequestBody PageModel<SysMessageCenter> pageModel) {
        final Map condition = pageModel.getCondition();
        //组装查询条件
        SysMessageCenter sysMessageCenter = new SysMessageCenter();
        if(condition != null){
            String jsonStr = JSON.toJSONString(condition);
            sysMessageCenter = JSON.parseObject(jsonStr, SysMessageCenter.class);
        }
        Page page = sysMessageCenterService.selectPageByEntity(pageModel,sysMessageCenter);
        return ResultUtil.ok(page);
     }

    /**
     * 新增消息中心表方法
     *
     * @param entity 消息中心表实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "新增消息中心表方法")
    @PostMapping("/add")
    @RequiresPermissions("sys:sysMessageCenter:update")
    @SysLogOpt(module = "新增消息中心表方法", value = "新增消息中心表方法", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@Valid @RequestBody SysMessageCenter entity) throws BusinessException {
        if (entity != null) {
	        entity.setRanking(MessageCenterRankingEnum.YES.getStatus());
	        entity.setStatus(MessageCenterStatusEnum.UNPUBLISH.getStatus());
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
        }
        return ResultUtil.ok(sysMessageCenterService.add(entity));
    }

    /**
     * 修改消息中心表方法
     *
     * @param entity 消息中心表实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "修改消息中心表方法")
    @PostMapping("/update")
    @SysLogOpt(module = "修改消息中心表方法", value = "修改消息中心表方法", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel update(@RequestBody SysMessageCenter entity) {
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());
        sysMessageCenterService.updateById(entity);

        updateMessageCenterForCahce(entity.getId());
        return ResultUtil.ok();
    }

    /**
     * 批量删除消息中心表方法
     *
     * @param ids 消息中心表ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-15
     */
    @ApiOperation(value = "批量删除消息中心表方法")
    @PostMapping("/delete")
    @RequiresPermissions("sys:sysMessageCenter:delete")
    @SysLogOpt(module = "批量删除消息中心表方法", value = "批量删除消息中心表方法", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        boolean b = sysMessageCenterService.deleteBatchIds(Arrays.asList(ids));
        updateMessageCenterForCahce(ids[0]);
        return ResultUtil.ok(b);
    }

    /**
     * 修改为上线状态
     *
     * @param ids ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "修改为上线状态")
    @PostMapping("/upline")
    @RequiresPermissions("sys:sysMessageCenter:downAndUp")
    @SysLogOpt(module = "修改为上线状态", value = "修改为上线状态", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel upline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);
        List<SysMessageCenter> updateList=new ArrayList<>();
        for(Long id:ids){
            SysMessageCenter update = new SysMessageCenter();
            update.setId(id);
            update.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysMessageCenterService.updateBatchById(updateList);
            updateMessageCenterForCahce(ids[0]);
        }

        return ResultUtil.ok(true);
    }


    /**
     * 修改为下线状态
     * @param ids
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "修改为下线状态")
    @PostMapping("/downline")
    @RequiresPermissions("sys:sysMessageCenter:downAndUp")
    @SysLogOpt(module = "修改为下线状态", value = "修改为下线状态", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel downline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);

        SysMessageCenter sysMessageCenter = sysMessageCenterService.selectById(ids[0]);
        if(sysMessageCenter==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到数据");
        }
        SysMessageCenter messageCenter = new SysMessageCenter();
        //服务协议
        messageCenter.setType(sysMessageCenter.getType());
        //已发布
        messageCenter.setStatus(MessageCenterStatusEnum.PUBLISH.getStatus());
        List<SysMessageCenter> list = sysMessageCenterService.selectList(messageCenter);
        if(list == null || list.size()<2){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "发布状态至少要存在一个！");
        }
        List<SysMessageCenter> updateList=new ArrayList<>();
        for(Long id:ids){
            SysMessageCenter update=new SysMessageCenter();
            update.setId(id);
            update.setStatus(MessageCenterStatusEnum.DOWNLINE.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysMessageCenterService.updateBatchById(updateList);
            updateMessageCenterForCahce(ids[0]);
        }

        return ResultUtil.ok(true);
    }
	
	/**
	 * 置顶或取消置顶
	 *
	 * @param id 主键
	 * @return com.yx.common.web.model.ResultModel
	 * @author lilulu
	 * @date 2018-10-24
	 */
	@ApiOperation(value = "置顶或取消置顶")
	@PostMapping("/ranking")
    @RequiresPermissions("sys:sysMessageCenter:rank")
	@SysLogOpt(module = "置顶或取消置顶", value = "置顶或取消置顶", operationType = Constants.LogOptEnum.MODIFY)
	public ResultModel upline(@RequestBody Long id) throws Exception{
		Assert.notNull(id);
		SysMessageCenter sysMessageCenter = sysMessageCenterService.selectById(id);
		Assert.notNull(sysMessageCenter);
		SysMessageCenter update = new SysMessageCenter();
		update.setId(id);
		update.setRanking(sysMessageCenter.getRanking() == 0 ? 1 : 0);
		update.setUpdateBy(getCurrentUserId());
		update.setUpdateTime(new Date());
		sysMessageCenterService.updateById(update);

        updateMessageCenterForCahce(id);
		return ResultUtil.ok();
	}

    /**
     * 更新行业资讯到redis
     * @param id
     */
	private void updateMessageCenterForCahce(Long id){
        SysMessageCenter sysMessageCenter = sysMessageCenterService.selectById(id);
        if(sysMessageCenter!=null&&sysMessageCenter.getType()!=null&&
                sysMessageCenter.getType()== MessageCenterEnum.INDUSTRY_INQUIRY.getStatus()){

            List<SysMessageCenter> sysMessageCenters = sysMessageCenterService.selectAuthorityInformation();
            CacheUtil.getCache().set(CacheKeyContstant.INDEX_INDUSTRY_INQUIRY,JSON.toJSONString(sysMessageCenters),30*60);
        }
    }



}

