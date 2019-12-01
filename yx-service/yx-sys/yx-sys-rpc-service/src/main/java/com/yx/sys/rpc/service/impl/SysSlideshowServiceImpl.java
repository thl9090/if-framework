package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.SysSlideshowMapper;
import com.yx.sys.model.SysSlideshow;
import com.yx.sys.rpc.api.SysSlideshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author lilulu
 * @since 2018-08-09
 */
@Service("sysSlideshowService")
public class SysSlideshowServiceImpl extends BaseServiceImpl<SysSlideshowMapper, SysSlideshow> implements SysSlideshowService {

    @Autowired
    private SysSlideshowMapper sysSlideshowMapper;

    @Override
    public Page selectPage(Page<SysSlideshow> page, SysSlideshow slideshow) {
        List<SysSlideshow> list = sysSlideshowMapper.selectPage(page,
                slideshow);
        return page.setRecords(list);
    }

    @Override
    public List<SysSlideshow> selectList(SysSlideshow slideshow) {
        return sysSlideshowMapper.selectList(slideshow);
    }

    @Override
    public SysSlideshow getOpenAdvertising() {
        return sysSlideshowMapper.getOpenAdvertising();
    }


    @Override
    public Page<SysSlideshow> selectActivityMsgPage(Page<SysSlideshow> page,
                                                    SysSlideshow sysSlideshow) {

        List<SysSlideshow> list =
                sysSlideshowMapper.selectActivityMsgPage(page, sysSlideshow);
        return page.setRecords(list);
    }


}
