package com.yx.sys.rpc.api;

import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysRoleMenu;

/**
 * <p>
 * 角色授权表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysRoleMenuService extends BaseService<SysRoleMenu> {

    /**
     * 根据角色ID批量删除角色和菜单的关系
     *
     * @param roleId
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean delBatchByRoleId(Long roleId);
}
