package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import lombok.Data;

/**
 * <p>
 * 用户授权表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@TableName("sys_user_menu")
public class SysUserMenu extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private Long userId;
    @TableField("menu_id")
    private Long menuId;
    /**
     * 权限标识
     */
    @TableField("permission_")
    private String permission;
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
