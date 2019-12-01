package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.model.SysRole;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 分页查找所有角色
     *
     * @param page
     * @return Page<SysRoleModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Page<SysRole> queryListPage(Page<SysRole> page);


    /**
     * 新增角色
     *
     * @param sysRoleModel
     * @return SysRoleModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Override
    SysRole add(SysRole sysRoleModel) throws BusinessException;

    /**
     * 根据角色ID修改
     *
     * @param sysRoleModel
     * @return SysRoleModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @Override
    SysRole modifyById(SysRole sysRoleModel) throws BusinessException;

    /**
     * 查询部门下所有角色
     *
     * @param deptId
     * @return List<SysRoleModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysRole> queryRoles(Long deptId);
}
