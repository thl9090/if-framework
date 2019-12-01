package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysLog;

/**
 * 日志业务类
 *
 * @author TangHuaLiang
 * @date 17/12/26 12:41:05
 */
public interface SysLogService extends BaseService<SysLog> {

    /**
     * 插入方法重写--因为表名是动态的
     * @param sysLog
     * @return
     */
    @Override
    boolean insert(SysLog sysLog);

    /**
     * 分页查询
     * @param page
     * @param sysLog
     * @return
     */
    Page<SysLog> selectPage(Page<SysLog> page, SysLog sysLog);




}
