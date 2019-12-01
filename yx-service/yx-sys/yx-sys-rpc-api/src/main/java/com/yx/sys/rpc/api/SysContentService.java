package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysContent;
import com.yx.sys.model.vo.SysContentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lilulu
 * @since 2018-07-25
 */
public interface SysContentService extends BaseService<SysContent> {

    Page<SysContent> selectPage(Page<SysContent> page, SysContent sysContent);

    Page<SysContent> selectVOPage(Page<SysContent> page, SysContent sysContent);

    SysContent selectOneData(SysContent sysContent);

    List<SysContent> selectList(SysContent content);
	
	List<SysContent> selectDevelopingCourse(String year);
	
	List<SysContentVO> selectDevelopingCourseYear();
	
}
