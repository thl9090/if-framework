package com.yx.sys.dao.mapper;

import com.yx.sys.model.SysTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 树 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
public interface SysTreeMapper extends com.baomidou.mybatisplus.mapper.BaseMapper<SysTree> {

    /**
     * 查询部门树
     *
     * @param id
     * @return java.util.List<com.yx.sys.model.SysTreeModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:10:01
     */
    List<SysTree> selectDeptTree(@Param("id") Long id);

    /**
     * 查询菜单树
     *
     * @param id
     * @param menuType
     * @return java.util.List<com.yx.sys.model.SysTreeModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:10:21
     */
    List<SysTree> selectMenuTree(@Param("id") Long id, @Param("menuType") Integer menuType);


    /**
     * 查询保障机构树
     * @param id
     * @return
     */
    List<SysTree> selectGuaranteeInfoTree(@Param("id") Long id);

    /**
     * 获取所有管理员
     * @return
     */
    List<SysTree> selectSysUserTree();

    /**
     * 获取内容父节点
     * @param map
     * @return
     */
    List<SysTree> selectContentTree(Map<String,Object> map);

    /**
     * 查询企业担保用户
     * @return
     */
    List<SysTree> selectGuaranteeUsertree();
}
