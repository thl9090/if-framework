package com.yx.sys.rpc.service.impl;

import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.SysRoleMenuMapper;
import com.yx.sys.model.SysRoleMenu;
import com.yx.sys.rpc.api.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色授权表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public boolean delBatchByRoleId(Long roleId) {
        return false;
    }
}
