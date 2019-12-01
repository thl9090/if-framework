package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表 Mapper 接口
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询
     *
     * @param page    分页实体
     * @param wrapper wrapper条件
     * @return List<SysRoleModel>
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    List<SysRole> selectRoleList(Pagination page, @Param("ew") Wrapper<SysRole> wrapper);
}
