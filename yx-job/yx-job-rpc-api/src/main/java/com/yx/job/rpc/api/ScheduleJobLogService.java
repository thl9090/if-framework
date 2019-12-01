package com.yx.job.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.yx.job.model.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	@Override
    Page<ScheduleJobLogEntity> selectPage(Page<ScheduleJobLogEntity> pageModel);

}
