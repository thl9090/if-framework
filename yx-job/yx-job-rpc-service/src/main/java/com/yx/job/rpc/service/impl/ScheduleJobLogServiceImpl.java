package com.yx.job.rpc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yx.job.dao.mapper.ScheduleJobLogMapper;
import com.yx.job.model.ScheduleJobLogEntity;
import com.yx.job.rpc.api.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogMapper, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public Page<ScheduleJobLogEntity> selectPage(Page<ScheduleJobLogEntity> pageModel) {
		ScheduleJobLogEntity entity=new ScheduleJobLogEntity();
		EntityWrapper<ScheduleJobLogEntity> entityWrapper = new EntityWrapper<>(entity);
		StringBuilder conditionSql = new StringBuilder();
		conditionSql.append("  1=1  AND ");
		if (ObjectUtil.isNotNull(pageModel.getCondition())) {
			Map<String, Object> paramMap = pageModel.getCondition();
			paramMap.forEach((k, v) -> {
				if (StrUtil.isNotBlank(v + "")) {
					conditionSql.append(k + " like '%" + v + "%' AND ");
				}
			});
		}
		entityWrapper.and(StrUtil.removeSuffix(conditionSql.toString(), "AND "));
		entityWrapper.orderBy("log_id",false);
		pageModel.setCondition(null);
		return super.selectPage(pageModel, entityWrapper);
	}

}
