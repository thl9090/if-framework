package com.yx.job.rpc.service.components;

/**
 * 定时建表组件
 *
 * @author YanBingHao
 * @since 2018/7/31
 */
public interface CreateTableComponents {

    /**
     * 创建鄂托克调用日志
     *
     * @param suffix
     */
    void createBankETKLogTable(String suffix) throws Exception;

    /**
     * 创建活动规则执行日志
     *
     * @param suffix
     */
    void createBankHXLogTable(String suffix) throws Exception;


    /**
     * 创建活动规则执行日志
     *
     * @param suffix
     */
    void createActivityRuleExecuteLogTable(String suffix) throws Exception;


    /**
     * 创建用户登陆日志
     */
    void createUserLoginLogTable(String suffix)throws Exception;

    /**
     * 创建系统日志
     */
    void createSysLogTable(String suffix)throws Exception;

}
