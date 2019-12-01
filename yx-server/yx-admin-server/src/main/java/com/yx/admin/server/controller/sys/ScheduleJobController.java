package com.yx.admin.server.controller.sys;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.model.PageModel;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.job.model.ScheduleJobEntity;
import com.yx.job.rpc.api.ScheduleJobService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 定时任务前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-10
 */
@Slf4j
@Api(value = "定时任务管理", description = "定时任务管理")
@RestController
@RequestMapping("/scheduleJob")
public class ScheduleJobController extends BaseController {
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
    * 根据定时任务ID查询
    *
    * @param id 定时任务ID
    * @return ResultModel
    * @author TangHuaLiang
    * @date 2018-07-10
    */
    @GetMapping("/select/{id}")
    @RequiresPermissions("sys:job:read")
    public ResultModel selectById(@PathVariable Long id) {
            Assert.notNull(id);
            ScheduleJobEntity entity = scheduleJobService.selectById(id);
            return ResultUtil.ok(entity);
    }

    /**
    * 查询定时任务分页方法
    *
    * @param pageModel 分页实体
    * @return com.yx.common.web.model.ResultModel
    * @author TangHuaLiang
    * @date 2018-07-10
    */
    @PostMapping("/selectPage")
    @RequiresPermissions("sys:job:read")
    public ResultModel selectPage(@RequestBody PageModel pageModel) {
        Page<ScheduleJobEntity> selectPage = scheduleJobService.selectPage(pageModel);
        return ResultUtil.ok(selectPage);
    }

    /**
     * 新增定时任务方法
     *
     * @param entity 定时任务实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-10
     */
    @PostMapping("/add")
    @RequiresPermissions("sys:job:update")
    public ResultModel add(@Valid @RequestBody ScheduleJobEntity entity) {
        if (entity != null) {
        	scheduleJobService.save(entity);
        	return ResultUtil.ok();
        }
        return ResultUtil.fail(null, "获取对象为null");

    }

    /**
     * 修改定时任务方法
     *
     * @param entity 定时任务实体
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-10
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:job:update")
    public ResultModel update(@RequestBody ScheduleJobEntity entity) {
        scheduleJobService.update(entity);
        return ResultUtil.ok(true);
    }

    /**
     * 批量删除定时任务方法
     *
     * @param ids 定时任务ID集合
     * @return com.yx.common.web.model.ResultModel
     * @author TangHuaLiang
     * @date 2018-07-10
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:job:delete")
    public ResultModel delete(@RequestBody Long[] ids) {
        Assert.notNull(ids);
        scheduleJobService.deleteBatch(ids);
        return ResultUtil.ok(true);
    }

    /**
     * 立即执行任务
     */
    @PostMapping("/run")
    @RequiresPermissions("sys:job:run")
    public ResultModel run(@RequestBody Long[] jobIds){
        scheduleJobService.run(jobIds);

        return ResultUtil.ok(true);
    }

    /**
     * 暂停定时任务
     */
    @PostMapping("/pause")
    @RequiresPermissions("sys:job:pauseAndResume")
    public ResultModel pause(@RequestBody Long[] jobIds){
        scheduleJobService.pause(jobIds);

        return ResultUtil.ok(true);
    }

    /**
     * 恢复定时任务
     */
    @PostMapping("/resume")
    @RequiresPermissions("sys:job:pauseAndResume")
    public ResultModel resume(@RequestBody Long[] jobIds){
        scheduleJobService.resume(jobIds);

        return ResultUtil.ok(true);
    }

}

