package com.yx.job.rpc.service.components.impl;

import com.yx.common.core.exception.BusinessException;
import com.yx.job.rpc.service.components.CreateTableComponents;
import com.yx.sys.model.SysParam;
import com.yx.sys.rpc.api.SysParamService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.yx.job.common.constant.CreateTableConstant.*;

@Slf4j
@Component
public class CreateTableComponentsImpl implements CreateTableComponents {


    private static final String BANK_HX_LOG_CREATE_TABLE_KEY = "BANK_HX_LOG_CREATE_TABLE_SQL";

    @Autowired
    @Qualifier("masterJdbcTemplate")
    private JdbcTemplate masterJdbcTemplate;

    @Autowired
    private SysParamService sysParamService;

    @Override
    public void createBankETKLogTable(String suffix) throws Exception {
        String exeSql = sysParamService.queryStringByKey(BANK_ETK_LOG_CREATE_TABLE_SQL_KEY);
        if (StringUtils.isBlank(exeSql)) {
            throw new BusinessException("华兴调用日志建表语句为空，无法创建表");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new BusinessException("华兴调用日志建表后缀为空，无法创建表");
        }
        exeSql = exeSql.replace("${date}", suffix);
        masterJdbcTemplate.execute(exeSql);
    }

    @Override
    public void createBankHXLogTable(String suffix) throws BusinessException {
        SysParam sysParam = sysParamService.queryByKey(BANK_HX_LOG_CREATE_TABLE_KEY);
        String paramValue = sysParam.getParamValue();
        if (StringUtils.isBlank(paramValue)) {
            throw new BusinessException("华兴调用日志建表语句为空，无法创建表");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new BusinessException("华兴调用日志建表后缀为空，无法创建表");
        }
        paramValue = paramValue.replace("${date}", suffix);
        masterJdbcTemplate.execute(paramValue);
    }


    @Override
    public void createActivityRuleExecuteLogTable(String suffix) throws Exception {
        String exeSql = sysParamService.queryStringByKey(ACTIVITY_RULE_EXECUTE_LOG_CREATE_TABLE_SQL_KEY);
        if (StringUtils.isBlank(exeSql)) {
            throw new BusinessException("活动规则执行日志建表语句为空，无法创建表");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new BusinessException("活动规则执行日志建表后缀为空，无法创建表");
        }
        exeSql = exeSql.replace("${date}", suffix);
        masterJdbcTemplate.execute(exeSql);
    }

    @Override
    public void createUserLoginLogTable(String suffix) throws Exception {
        SysParam sysParam = sysParamService.queryByKey(USER_LOGIN_LOG_CREATE_TABLE_SQL_KEY);
        String paramValue = sysParam.getParamValue();
        if (StringUtils.isBlank(paramValue)) {
            throw new BusinessException("用户登陆日志日志建表语句为空，无法创建表");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new BusinessException("用户登陆日志建表后缀为空，无法创建表");
        }
        paramValue = paramValue.replace("${date}", suffix);
        masterJdbcTemplate.execute(paramValue);
    }

    @Override
    public void createSysLogTable(String suffix) throws Exception {
        SysParam sysParam = sysParamService.queryByKey(SYS_LOG_CREATE_TABLE_SQL_KEY);
        String paramValue = sysParam.getParamValue();
        if (StringUtils.isBlank(paramValue)) {
            throw new BusinessException("系统操作日志建表语句为空，无法创建表");
        }
        if (StringUtils.isBlank(suffix)) {
            throw new BusinessException("系统操作日志建表后缀为空，无法创建表");
        }
        paramValue = paramValue.replace("${date}", suffix);
        masterJdbcTemplate.execute(paramValue);
    }
}
