package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysSlideshow;

import java.util.List;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author lilulu
 * @since 2018-08-09
 */
public interface SysSlideshowService extends BaseService<SysSlideshow> {

    Page<SysSlideshow> selectPage(Page<SysSlideshow> page, SysSlideshow slideshow);

    List<SysSlideshow> selectList(SysSlideshow slideshow);

    SysSlideshow getOpenAdvertising();

    Page<SysSlideshow> selectActivityMsgPage(Page<SysSlideshow> page, SysSlideshow sysSlideshow);

}
