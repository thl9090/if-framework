package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import lombok.Data;

/**
 * <p>
 * 部门
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@TableName("sys_dept")
public class SysDept extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 隶属单位
     */
    @TableField("unit_id")
    private Long unitId;
    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;
    /**
     * 上级部门编号
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;
    /**
     * 排序号
     */
    @TableField("sort_no")
    private Integer sortNo;
    /**
     * 叶子节点(0:树枝节点;1:叶子节点)
     */
    @TableField("leaf_")
    private Integer leaf;
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
}
