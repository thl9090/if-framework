package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.ExportLogMapper;
import com.yx.sys.model.ExportLog;
import com.yx.sys.rpc.api.ExportLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 导出日志记录表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */
@Service("exportLogService")
public class ExportLogServiceImpl extends BaseServiceImpl<ExportLogMapper, ExportLog> implements ExportLogService {

    @Autowired
    private ExportLogMapper exportLogMapper;


    @Override
    public Page<ExportLog> selectPage(Page<ExportLog> page, ExportLog exportLog) {
        List<ExportLog> list = exportLogMapper.selectPage(page, exportLog);
        page.setRecords(list);
        return page;
    }

    @Override
    public ExportLog selectLateByUserIdAndType(Long userId, Integer type) {
        return exportLogMapper.selectLateByUserIdAndType(userId,type);
    }


}
