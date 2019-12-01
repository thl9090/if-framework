package com.yx.admin.server.controller.sys;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.model.PageModel;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ClientTypeEnum;
import com.yx.sys.common.SlideShowStatusEnum;
import com.yx.sys.common.SlideshowTypeEnum;
import com.yx.sys.common.constant.CacheKeyContstant;
import com.yx.sys.model.SysSlideshow;
import com.yx.sys.rpc.api.SysSlideshowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 轮播图前端控制器
 * </p>
 *
 * @author lilulu
 * @since 2018-08-09
 */
@Slf4j
@RestController
@RequestMapping("/sysSlideshow")
@Api(value = "轮播图", description = "轮播图")
public class SysSlideshowController extends BaseController {

    @Autowired
    private SysSlideshowService sysSlideshowService;

    /**
    * 根据轮播图ID查询
    *
    * @param id 轮播图ID
    * @return ResultModel
    * @author lilulu
    * @date 2018-08-09
    */
    @ApiOperation(value = "根据轮播图ID查询")
    @GetMapping("/select/{id}")
    @RequiresPermissions("sys:sysSlideshow:read")
    @SysLogOpt(module = "根据轮播图ID查询", value = "根据轮播图ID查询", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectById(@PathVariable Long id) {
            Assert.notNull(id);
            SysSlideshow entity = sysSlideshowService.selectById(id);
            return ResultUtil.ok(entity);
    }

    /**
    * 查询轮播图分页方法
    *
    * @param pageModel 分页实体
    * @return com.yx.common.web.model.ResultModel
    * @author lilulu
    * @date 2018-08-09
    */
    @ApiOperation(value = "查询轮播图分页方法")
    @PostMapping("/selectPage")
    @RequiresPermissions("sys:sysSlideshow:read")
    @SysLogOpt(module = "查询轮播图分页方法", value = "查询轮播图分页方法", operationType = Constants.LogOptEnum.QUERY)
    public ResultModel selectPage(@RequestBody PageModel<SysSlideshow> pageModel) {
        final Map condition = pageModel.getCondition();
        //组装查询条件
        SysSlideshow slideshow = new SysSlideshow();
        if(condition != null){
            String jsonStr = JSON.toJSONString(condition);
            slideshow = JSON.parseObject(jsonStr, SysSlideshow.class);
        }
        slideshow.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
        Page page = sysSlideshowService.selectPage(pageModel,slideshow);
        return ResultUtil.ok(page);
    }

    /**
     * 新增轮播图方法
     *
     * @param entity 轮播图实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "新增轮播图方法")
    @PostMapping("/add")
    @RequiresPermissions("sys:sysSlideshow:update")
    @SysLogOpt(module = "新增轮播图方法", value = "新增轮播图方法", operationType = Constants.LogOptEnum.ADD)
    public ResultModel add(@RequestBody SysSlideshow entity) throws Exception {
        if (entity != null) {
            //设置轮播图
            entity.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
            entity.setCreateBy(this.getCurrentUserId());
            entity.setUpdateBy(this.getCurrentUserId());
        }
        return ResultUtil.ok(sysSlideshowService.add(entity));
    }

    /**
     * 修改轮播图方法
     * @param entity 轮播图实体
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "修改轮播图方法")
    @PostMapping("/update")
    @RequiresPermissions("sys:sysSlideshow:update")
    @SysLogOpt(module = "修改轮播图方法", value = "修改轮播图方法", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel update(@RequestBody SysSlideshow entity) {
        //设置轮播图
        entity.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
        entity.setUpdateBy(this.getCurrentUserId());
        entity.setUpdateTime(new Date());
        sysSlideshowService.updateById(entity);
        //修改轮播图后更新redis中的数据
        updateSlideShowForCache(entity.getId());

        return ResultUtil.ok();
    }

    /**
     * 批量删除轮播图方法
     *
     * @param ids 轮播图ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "批量删除轮播图方法")
    @PostMapping("/delete")
    @RequiresPermissions("sys:sysSlideshow:delete")
    @SysLogOpt(module = "批量删除轮播图方法", value = "批量删除轮播图方法", operationType = Constants.LogOptEnum.DELETE)
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        return ResultUtil.ok(sysSlideshowService.deleteBatchIds(Arrays.asList(ids)));
    }

    /**
     * 轮播图下线
     * @param ids
     * @return com.yx.common.web.model.ResultModel
     * @author lilulu
     * @date 2018-08-09
     */
    @ApiOperation(value = "轮播图下线")
    @PostMapping("/downline")
    @RequiresPermissions("sys:sysSlideshow:downAndUp")
    @SysLogOpt(module = "轮播图下线", value = "轮播图下线", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel downline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);

        SysSlideshow sysSlideshow = sysSlideshowService.selectById(ids[0]);
        if(sysSlideshow==null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到数据");
        }
        SysSlideshow slideshow = new SysSlideshow();
        //轮播图
        slideshow.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
        //移动端
        slideshow.setClientType(sysSlideshow.getClientType());
        //已发布
        slideshow.setStatus(SlideShowStatusEnum.PUBLISH.getStatus());
        List<SysSlideshow> list = sysSlideshowService.selectList(slideshow);
        if(list.size()<2){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "每个端发布状态的轮播图至少要存在一个！");
        }
        List<SysSlideshow> updateList=new ArrayList<>();
        for(Long id:ids){
            SysSlideshow update=new SysSlideshow();
            update.setId(id);
            update.setStatus(SlideShowStatusEnum.DOWNLINE.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysSlideshowService.updateBatchById(updateList);
        }

        //更新数据到缓存
        updateSlideShowForCache(ids[0]);

        return ResultUtil.ok(true);
    }

    /**
     * 轮播图上线
     * @param ids
     * @return
     */
    @ApiOperation(value = "轮播图上线")
    @PostMapping("/upline")
    @RequiresPermissions("sys:sysSlideshow:downAndUp")
    @SysLogOpt(module = "轮播图上线", value = "轮播图上线", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel upline(@RequestBody Long[] ids) throws Exception{
        Assert.notNull(ids);
        List<SysSlideshow> updateList=new ArrayList<>();
        for(Long id:ids){
            SysSlideshow update=new SysSlideshow();
            update.setId(id);
            update.setStatus(SlideShowStatusEnum.PUBLISH.getStatus());
            update.setUpdateBy(getCurrentUserId());
            update.setUpdateTime(new Date());
            updateList.add(update);
        }
        if(updateList.size()>0){
            sysSlideshowService.updateBatchById(updateList);
        }
        //更新数据到缓存
        updateSlideShowForCache(ids[0]);


        return ResultUtil.ok(true);
    }


    /**
     * 更新缓存中的轮播图数据
     * @param id
     */
    private void updateSlideShowForCache(Long id){
        SysSlideshow sysSlideshow = sysSlideshowService.selectById(id);
        if(sysSlideshow!=null&&sysSlideshow.getClientType()!=null){
            SysSlideshow slideshow = new SysSlideshow();
            //轮播图
            slideshow.setType(SlideshowTypeEnum.CAROUSEL.getStatus());
            //已发布
            slideshow.setStatus(SlideShowStatusEnum.PUBLISH.getStatus());
            if(ClientTypeEnum.PC.getStatus().equals(sysSlideshow.getClientType())){
                //移动端
                slideshow.setClientType(ClientTypeEnum.PC.getStatus());
                List<SysSlideshow> slideshowVOS = sysSlideshowService.selectList(slideshow);
                CacheUtil.getCache().set(CacheKeyContstant.SYS_SLIDESHOW_LIST_PC,JSON.toJSONString(slideshowVOS),30*60);
            }else if(ClientTypeEnum.WX.getStatus().equals(sysSlideshow.getClientType())){
                //移动端
                slideshow.setClientType(ClientTypeEnum.WX.getStatus());
                List<SysSlideshow> slideshowVOS = sysSlideshowService.selectList(slideshow);
                CacheUtil.getCache().set(CacheKeyContstant.SYS_SLIDESHOW_LIST_WX,JSON.toJSONString(slideshowVOS),30*60);
            }else if(ClientTypeEnum.APP.getStatus().equals(sysSlideshow.getClientType())){
                //移动端
                slideshow.setClientType(ClientTypeEnum.APP.getStatus());
                List<SysSlideshow> slideshowVOS = sysSlideshowService.selectList(slideshow);
                CacheUtil.getCache().set(CacheKeyContstant.SYS_SLIDESHOW_LIST_APP,JSON.toJSONString(slideshowVOS),30*60);
            }
        }
    }


}

