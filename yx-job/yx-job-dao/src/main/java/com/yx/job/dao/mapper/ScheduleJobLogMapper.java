package com.yx.job.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yx.job.model.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {

}
