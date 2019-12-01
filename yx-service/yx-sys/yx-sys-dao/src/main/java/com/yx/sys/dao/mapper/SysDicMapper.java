package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysDic;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 数据字典明细表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysDicMapper extends BaseMapper<SysDic> {


    SysDic selectByTypeAndCode(@Param("type") String type,@Param("code")Integer code);
}
