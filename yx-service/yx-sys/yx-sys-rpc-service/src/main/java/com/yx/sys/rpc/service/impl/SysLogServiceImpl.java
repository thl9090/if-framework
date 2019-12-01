package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.utils.DateUtils;
import com.yx.sys.dao.mapper.SysLogMapper;
import com.yx.sys.model.SysLog;
import com.yx.sys.rpc.api.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yx.common.utils.DateUtils.MONTH_PARSE;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Service("sysLogService")
@Slf4j
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public boolean insert(SysLog sysLog){
        try {
            sysLog.setTableName("sys_log_" + DateUtils.getDate(MONTH_PARSE));
            Integer insert = sysLogMapper.insert(sysLog);
            if (insert > 0) {
                return true;
            }
            return false;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Page<SysLog> selectPage(Page<SysLog> page, SysLog sysLog) {
        sysLog.setTableName("sys_log_" + DateUtils.getDate(MONTH_PARSE));
        List<SysLog> list = sysLogMapper.selectPage(page, sysLog);
        page.setRecords(list);
        return page;
    }
}
