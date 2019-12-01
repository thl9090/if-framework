package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色授权表 Mapper 接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色ID查询对应菜单ID集合
     *
     * @param roleId 角色ID
     * @return List<Long>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<Long> selectMenuIdListByRoleId(@Param("roleId") Long roleId);
}
