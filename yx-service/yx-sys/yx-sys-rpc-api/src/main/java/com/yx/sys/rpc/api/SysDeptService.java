package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.model.SysDept;
import com.yx.sys.model.SysTree;

import java.util.List;

/**
 * 部门管理服务接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysDeptService extends BaseService<SysDept> {

    /**
     * 添加部门
     *
     * @param sysDeptModel
     * @return com.yx.sys.model.SysDeptModel
     * @author TangHuaLiang
     * @date 18/1/1 15:04:47
     */
    public SysDept addDept(SysDept sysDeptModel) throws BusinessException;

    /**
     * 修改部门
     *
     * @param sysDeptModel
     * @return boolean
     * @author TangHuaLiang
     * @date 18/1/1 15:05:10
     */
    public boolean modifyDept(SysDept sysDeptModel);

    /**
     * 分页查询部门
     *
     * @param page
     * @return com.baomidou.mybatisplus.plugins.Page<com.yx.sys.model.SysDeptModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:05:30
     */
    Page<SysDept> queryListPage(Page<SysDept> page);

    /**
     * 查询单个部门
     *
     * @param id
     * @return com.yx.sys.model.SysDeptModel
     * @author TangHuaLiang
     * @date 18/1/1 15:05:53
     */
    public SysDept queryOne(Long id);

    /**
     * 批量删除部门
     *
     * @param ids
     * @return java.lang.Integer
     * @author TangHuaLiang
     * @date 18/1/1 15:06:11
     */
    public Integer deleteBatch(Long[] ids);

    /**
     * 查询部门数
     *
     * @param
     * @return java.util.List<com.yx.sys.model.SysTreeModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:06:33
     */
    public List<SysTree> queryTree();
    /**
     * 查询部门数，过滤该id下所有部门
     *
     * @param id
     * @return java.util.List<com.yx.sys.model.SysTreeModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:06:33
     */
    public List<SysTree> queryTree(Long id);

    /**
     * 删除部门
     *
     * @param id
     * @return boolean
     * @author TangHuaLiang
     * @date 18/1/1 15:07:49
     */
    public boolean delDept(Long id) throws BusinessException;

    /**
     * 按id查询子部门
     *
     * @param id
     * @return java.util.List<com.yx.sys.model.SysDeptModel>
     * @author TangHuaLiang
     * @date 18/1/1 15:08:09
     */
    public List<SysDept> querySubDept(Long id);

    /**
     * 查询子部门数量
     *
     * @param id
     * @return int
     * @author TangHuaLiang
     * @date 18/1/1 15:08:35
     */
    public int querySubDeptCount(Long id);

    /**
     * 查询子部门数量
     *
     * @param ids
     * @return int
     * @author TangHuaLiang
     * @date 18/1/1 15:08:53
     */
    public int querySubDeptCount(Long[] ids);
}
