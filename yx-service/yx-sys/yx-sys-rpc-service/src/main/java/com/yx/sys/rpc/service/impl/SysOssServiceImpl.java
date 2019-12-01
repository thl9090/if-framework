package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.SysOssMapper;
import com.yx.sys.model.SysOss;
import com.yx.sys.model.vo.SysOssVO;
import com.yx.sys.rpc.api.SysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-29
 */
@Service("sysOssService")
public class SysOssServiceImpl extends BaseServiceImpl<SysOssMapper, SysOss> implements SysOssService {

    @Autowired
    private SysOssMapper sysOssMapper;

    @Override
    public Page<SysOssVO> selectVOPage(Page<SysOssVO> page, SysOssVO sysOssVO) {
        List<SysOssVO> list = sysOssMapper.selectVOPage(page, sysOssVO);
        page.setRecords(list);
        return page;
    }
}
