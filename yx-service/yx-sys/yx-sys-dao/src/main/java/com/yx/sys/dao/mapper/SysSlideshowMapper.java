package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysSlideshow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 轮播图 Mapper 接口
 * </p>
 *
 * @author lilulu
 * @since 2018-08-09
 */
public interface SysSlideshowMapper extends BaseMapper<SysSlideshow> {

    List<SysSlideshow> selectPage(@Param("page") Page<SysSlideshow> page, @Param("slideshow") SysSlideshow slideshow);

    List<SysSlideshow> selectList(@Param("slideshow") SysSlideshow slideshow);

    /**
     * 查询开屏广告（最新发布的一张图片）
     * @return
     */
    SysSlideshow getOpenAdvertising();

    List<SysSlideshow> selectActivityMsgPage(@Param("page") Page<SysSlideshow> page, @Param("sysSlideshow")SysSlideshow sysSlideshow);

}
