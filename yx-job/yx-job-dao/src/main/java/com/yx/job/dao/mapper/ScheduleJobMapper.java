package com.yx.job.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yx.job.model.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity> {

	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
