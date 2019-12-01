package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单树
     *
     * @param userId
     * @return List<SysMenuModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId);

    /**
     * 查询系统菜单列表（分页）
     * @param page
     * @param sysMenu
     * @return
     */
    List<SysMenu> selectVOByPage(Page<SysMenu> page, @Param("sysMenu") SysMenu sysMenu);

}
