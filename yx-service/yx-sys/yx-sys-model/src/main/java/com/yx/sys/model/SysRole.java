package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 所属部门编号
     */
    @TableField("dept_id")
    private Long deptId;
    /**
     * 所属部门名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 角色类型(1:业务角色;2:管理角色 ;3:系统内置角色)
     */
    @TableField("role_type")
    private Integer roleType;
    /**
     * 是否启用
     */
    @TableField("enable_")
    private Integer enable;
    /**
     * 是否删除(0:未删除;1:已删除)
     */
    @TableField("is_del")
    private Integer isDel;
    /**
     * 备注
     */
    @TableField("remark_")
    private String remark;
    /**
     * 角色对应的功能
     */
    @TableField(exist = false)
    private List<Long> menuIdList;
}
