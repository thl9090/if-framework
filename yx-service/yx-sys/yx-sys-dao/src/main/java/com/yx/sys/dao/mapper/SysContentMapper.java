package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysContent;
import com.yx.sys.model.vo.SysContentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lilulu
 * @since 2018-07-25
 */
public interface SysContentMapper extends BaseMapper<SysContent> {

    List<SysContent> selectPage(Page<SysContent> page, @Param("sysContent") SysContent sysContent);

    List<SysContent> selectVOPage(Page<SysContent> page, @Param("sysContent") SysContent sysContent);

    SysContent selectOneData(@Param("sysContent") SysContent sysContent);

    List<SysContent> selectList(@Param("content") SysContent content);
	
	List<SysContent> selectDevelopingCourse(@Param("year") String year);
	
	List<SysContentVO> selectDevelopingCourseYear();
	
}
