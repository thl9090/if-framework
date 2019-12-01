package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.sys.model.SysRole;
import com.yx.sys.model.SysTree;
import com.yx.sys.model.SysUser;
import com.yx.sys.model.SysUserRole;
import com.yx.sys.model.vo.SysUserVO;

import java.util.List;

/**
 * 用户管理服务
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysUserService extends BaseService<SysUser> {

	/**
     * 根据用户ID查询
     * @param id
     * @return
     */
    SysUser selectById(Long id);

    /**
     * 根据用户ID查询(扩展类)
     * @param id
     * @return
     */
    SysUserVO selectVOById(Long id);


    /**
     * 分页查询用户(基础数据)
     * @param page
     * @param sysUser
     * @return
     */
    Page<SysUser> selectPage(Page<SysUser> page,SysUser sysUser);

    /**
     * 分页查询用户（扩展数据）
     * @param page
     * @param sysUserVO
     * @return
     */
    Page<SysUserVO> selectVOPage(Page<SysUserVO> page,SysUserVO sysUserVO);


    /**
     * 修改
     *
     * @param sysUser 用户实体
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean update(SysUser sysUser);


    /**
     * 根据账号查找用户
     *
     * @param account 账号
     * @return SysUser
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    SysUser selectByAccount(String account);


    /**
     * 根据ID集合批量删除
     *
     * @param ids 用户ID集合
     * @return boolean
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    boolean delBatchByIds(List<Long> ids);

    /**
     * 根据部门ID查询角色
     *
     * @param deptId 部门ID
     * @return java.util.List<com.yx.sys.model.SysRoleModel>
     * @author TangHuaLiang
     * @date 17/12/25 15:47:20
     */
    List<SysRole> selectRolesByDeptId(Long deptId);

    /**
     * 根据用户查询用户角色关系
     *
     * @param userId 用户ID
     * @return java.util.List<com.yx.sys.model.SysUserRole>
     * @author TangHuaLiang
     * @date 17/12/25 21:20:55
     */
    List<SysUserRole> selectUserRoles(Long userId);


    /**
     * 获取管理员名单
     * @return
     */
    List<SysTree> queryTree();

    /**
     * 查询企业担保用户信息
     * @return
     */
    List<SysTree> queryGuaranteeUsertree();
}
