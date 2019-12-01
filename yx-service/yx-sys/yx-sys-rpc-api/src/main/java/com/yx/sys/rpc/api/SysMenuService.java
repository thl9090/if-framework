package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.model.SysMenu;
import com.yx.sys.model.SysTree;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 查找所有菜单
     *
     * @return List<SysMenuModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysMenu> queryList();

    /**
     * 分页查询所有菜单
     *
     * @param page
     * @return Page<SysMenuModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Page<SysMenu> queryListPage(Page<SysMenu> page);

    /**
     * 根据用户ID查找菜单树（包含目录和菜单，不包含按钮）
     *
     * @param userId
     * @return List<SysMenuModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysTree> queryMenuTreeByUserId(Long userId);

    /**
     * 查找功能菜单树（包含目录、菜单和按钮）
     *
     * @return List<TreeModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysTree> queryFuncMenuTree();

    /**
     * 根据角色ID查找功能菜单树（包含目录、菜单和按钮）
     *
     * @param roleId
     * @return List<TreeModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysTree> queryFuncMenuTree(Long roleId);

    /**
     * 查询菜单树，供页面选择父菜单使用，过滤自己及子菜单
     *
     * @param  id
     * @param  menuType
     * @return  List<SysTreeModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysTree> queryTree(Long id, Integer menuType);

    /**
     * 删除单个菜单（设置删除状态为是）
     *
     * @param id 菜单编号
     * @return Boolean 删除成功返回true,否则返回false
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Boolean delete(Long id) throws BusinessException;

    /**
     * 批量删除菜单
     *
     * @param ids 要删除的菜单编号数组
     * @return 返回删除成功记录数
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    Integer deleteBatch(Long[] ids);

    /**
     * 菜单列表分页查询
     * @param page
     * @param sysMenu
     * @return
     */
    Page<SysMenu> selectVOByPage(Page<SysMenu> page,SysMenu sysMenu);


}
