package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.SysContentMapper;
import com.yx.sys.model.SysContent;
import com.yx.sys.model.vo.SysContentVO;
import com.yx.sys.rpc.api.SysContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lilulu
 * @since 2018-07-25
 */
@Service("sysContentService")
public class SysContentServiceImpl extends BaseServiceImpl<SysContentMapper, SysContent> implements SysContentService {

    @Autowired
    private SysContentMapper sysContentMapper;

    @Override
    public Page<SysContent> selectPage(Page<SysContent> page, SysContent sysContent) {
        List<SysContent> list = sysContentMapper.selectPage(page,sysContent);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<SysContent> selectVOPage(Page<SysContent> page, SysContent sysContent) {
        List<SysContent> list = sysContentMapper.selectVOPage(page, sysContent);
        page.setRecords(list);
        return page;
    }


    @Override
    public SysContent selectOneData(SysContent sysContent) {
        return sysContentMapper.selectOneData(sysContent);
    }

    @Override
    public List<SysContent> selectList(SysContent content) {
        return sysContentMapper.selectList(content);
    }
    
    @Override
    public List<SysContent> selectDevelopingCourse(String year) {
        return sysContentMapper.selectDevelopingCourse(year);
    }
    
    @Override
    public List<SysContentVO> selectDevelopingCourseYear() {
        return this.sysContentMapper.selectDevelopingCourseYear();
    }
}
