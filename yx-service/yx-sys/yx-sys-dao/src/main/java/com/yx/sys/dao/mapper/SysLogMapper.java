package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    /**
     * 插入方法（重写）
     * @param sysLog
     * @return
     */
    @Override
    Integer insert(SysLog sysLog);

    /**
     * 分页查询
     * @param page
     * @param sysLog
     * @return
     */
    List<SysLog> selectPage(Page<SysLog> page, @Param("sysLog") SysLog sysLog);



}
