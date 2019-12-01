package com.yx.admin.server.controller.sys;


import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ContentModelTypeEnum;
import com.yx.sys.common.SlideShowStatusEnum;
import com.yx.sys.common.SysContentStatusEnum;
import com.yx.sys.model.SysContent;
import com.yx.sys.rpc.api.SysContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lilulu
 * @since 2018-07-25
 */
@Slf4j
@RestController
@RequestMapping("/sysContent")
@Api(value = "内容管理", description = "内容管理")
public class SysContentController extends BaseController {
    @Autowired
    private SysContentService sysContentService;

    /**
     * 根据内容标ID查询
     *
     * @param id 内容标ID
     * @return ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "根据内容标ID查询")
    @GetMapping("/select/{id}")
    @SysLogOpt(module = "根据内容标ID查询", value = "根据内容标ID查询", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectById(@PathVariable Long id) {
        Assert.notNull(id);
        SysContent entity = sysContentService.selectById(id);
        return ResultUtil.ok(entity);
    }

    /**
     * 查询协议分页方法
     *
     * @param pageModel 查询服务协议分页实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "查询协议分页方法")
    @PostMapping("/selectAgreementPage")
    @SysLogOpt(module = "查询协议分页方法", value = "查询协议分页方法", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectAgreementPage(@RequestBody PageModel<SysContent> pageModel) {
        final Map condition = pageModel.getCondition();
        //组装查询条件
        SysContent sysContent = new SysContent();
        if(condition != null){
            String jsonStr = JSON.toJSONString(condition);
            sysContent = JSON.parseObject(jsonStr, SysContent.class);
        }
        //设置查询条件为注册协议
        sysContent.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
        Page page = sysContentService.selectPage(pageModel,sysContent);
        return ResultUtil.ok(page);
    }

    /**
     * 内容新增
     *
     * @param entity 内容实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "内容新增")
    @PostMapping("/add")
    @SysLogOpt(module = "内容新增", value = "内容新增", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@Valid @RequestBody SysContent entity) throws Exception {
        if (entity != null) {
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
            //设置为注册协议
            entity.setModelType(ContentModelTypeEnum.AGREEMENT.getStatus());
            entity.setStatus(SysContentStatusEnum.UNPUBLISH.getStatus());
        }
        return ResultUtil.ok(sysContentService.add(entity));
    }

    /**
     * 修改内容标方法
     *
     * @param entity 内容标实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "修改内容标方法")
    @PostMapping("/update")
    @SysLogOpt(module = "修改内容标方法", value = "修改内容标方法", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel update(@RequestBody SysContent entity) {
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());
        sysContentService.updateById(entity);
        return ResultUtil.ok();
    }

    /**
     * 批量删除内容标方法
     *
     * @param ids 内容标ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "批量删除内容标方法")
    @PostMapping("/delete")
    @SysLogOpt(module = "批量删除内容标方法", value = "批量删除内容标方法", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        return ResultUtil.ok(sysContentService.deleteBatchIds(Arrays.asList(ids)));
    }

    /**
     * 修改为上线状态
     *
     * @param ids 内容标ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-20
     */
    @ApiOperation(value = "修改为上线状态")
    @PostMapping("/upline")
    @SysLogOpt(module = "修改为上线状态", value = "修改为上线状态", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel upline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);
        List<SysContent> updateList=new ArrayList<>();
        for(Long id:ids){
            SysContent update = new SysContent();
            update.setId(id);
            update.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysContentService.updateBatchById(updateList);
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
    @SysLogOpt(module = "修改为下线状态", value = "修改为下线状态", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel downline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);

        SysContent sysContent = sysContentService.selectById(ids[0]);
        if(sysContent==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到数据");
        }
        SysContent content = new SysContent();
        if(sysContent.getStatus() != ContentModelTypeEnum.RECRUITMENT.getStatus()){
            //服务协议
            content.setModelType(sysContent.getModelType());
            //服务协议中的类型
            content.setModelTypeTypes(sysContent.getModelTypeTypes());
            //移动端
            content.setClientType(sysContent.getClientType());
            //已发布
            content.setStatus(SysContentStatusEnum.PUBLISH.getStatus());
            List<SysContent> list = sysContentService.selectList(content);
            if(list == null || list.size()<2){
                return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "发布状态至少要存在一个！");
            }
        }
        //招聘信息可以没有
        List<SysContent> updateList=new ArrayList<>();
        for(Long id:ids){
            SysContent update=new SysContent();
            update.setId(id);
            update.setStatus(SlideShowStatusEnum.DOWNLINE.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysContentService.updateBatchById(updateList);
        }
        return ResultUtil.ok(true);
    }

}

