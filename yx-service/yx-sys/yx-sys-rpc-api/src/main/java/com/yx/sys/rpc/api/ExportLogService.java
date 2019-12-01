package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.ExportLog;

/**
 * <p>
 * 导出日志记录表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */
public interface ExportLogService extends BaseService<ExportLog> {

    /**
     * 分页查询
     * @param page
     * @param exportLog
     * @return
     */
    Page<ExportLog> selectPage(Page<ExportLog> page, ExportLog exportLog);

    /**
     * 根据用户id和导出类型，查询最新的一条记录
     * @param userId
     * @param type
     * @return
     */
    ExportLog selectLateByUserIdAndType(Long userId, Integer type);
}
