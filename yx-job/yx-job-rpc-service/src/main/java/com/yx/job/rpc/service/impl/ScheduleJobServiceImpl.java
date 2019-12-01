package com.yx.job.rpc.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yx.job.common.enums.ScheduleStatusEnum;
import com.yx.job.dao.mapper.ScheduleJobMapper;
import com.yx.job.model.ScheduleJobEntity;
import com.yx.job.rpc.api.ScheduleJobService;
import com.yx.job.rpc.service.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJobEntity> implements ScheduleJobService {
	@Autowired
    private Scheduler scheduler;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = this.selectList(null);
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}

	@Override
	public Page<ScheduleJobEntity> selectPage(Page<ScheduleJobEntity> pageModel) {
		ScheduleJobEntity entity=new ScheduleJobEntity();
		EntityWrapper<ScheduleJobEntity> entityWrapper = new EntityWrapper<>(entity);
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
		entityWrapper.orderBy("job_id",false);
		pageModel.setCondition(null);
		return super.selectPage(pageModel, entityWrapper);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatusEnum.NORMAL.getId());
        this.insert(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);

        this.updateById(scheduleJob);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}

    	//删除数据
    	this.deleteBatchIds(Arrays.asList(jobIds));
	}

	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return baseMapper.updateBatch(map);
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, this.selectById(jobId));
    	}
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatusEnum.PAUSE.getId());
    }

	@Override
	@Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatusEnum.NORMAL.getId());
    }

}
