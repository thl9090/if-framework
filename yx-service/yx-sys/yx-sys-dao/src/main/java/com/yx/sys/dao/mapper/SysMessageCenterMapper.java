package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysMessageCenter;
import com.yx.sys.model.vo.SysMessageCenterVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 消息中心表 Mapper 接口
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
public interface SysMessageCenterMapper extends BaseMapper<SysMessageCenter> {

    SysMessageCenterVO selectVOById(@Param("id") Long id);

    List<SysMessageCenter> selectPageByEntity(@Param("page") Page<SysMessageCenter> page, @Param("params") SysMessageCenter sysMessageCenter);

    List<SysMessageCenter> selectList(@Param("messageCenter") SysMessageCenter messageCenter);
	
	List<SysMessageCenter> selectAuthorityInformation(@Param("sysMessageCenter") SysMessageCenter sysMessageCenter);
	
}
