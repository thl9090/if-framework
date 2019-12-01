package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门 Mapper 接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 分页查询
     * @param page 分页参数
     * @param deptName 部门名称
     * @return List<SysDeptModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysDept> selectPage(Page<SysDept> page, @Param("deptName") String deptName);

    /**
     * 根据部门ID查询
     *
     * @param id 部门ID
     * @return SysDeptModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    SysDept selectOne(@Param("id") Long id);
}
