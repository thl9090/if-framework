package com.yx.sys.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@TableName("sys_user")
@ApiModel(value="SysUserModel",description="系统用户")
public class SysUser extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆帐户
     */
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 20, message = "账号长度必须在4至20之间")
    @TableField("account_")
    @ApiModelProperty(value="账号")
    private String account;
    /**
     * 密码
     */
    @TableField("password_")
    @ApiModelProperty(value="密码")
    private String password;
    /**
     * 旧密码
     */
    @TableField(exist = false)
    @ApiModelProperty(value="旧密码")
    private String oldPassword;
    /**
     * 用户类型(1普通用户2管理员3系统用户)
     */
    @TableField("user_type")
    @ApiModelProperty(value="用户类型(1普通用户2管理员3系统用户)")
    private String userType;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(min = 2, max = 8, message = "姓名长度必须在2至8之间")
    @TableField("user_name")
    @ApiModelProperty(value="用户姓名")
    private String userName;
    /**
     * 姓名拼音
     */
    @TableField("name_pinyin")
    @ApiModelProperty(value="姓名拼音")
    private String namePinyin;
    /**
     * 性别(0:未知;1:男;2:女)
     */
    @TableField("sex_")
    @ApiModelProperty(value="性别(0:未知;1:男;2:女)")
    private Integer sex;
    /**
     * 头像
     */
    @TableField("avatar_")
    @ApiModelProperty(value="头像")
    private String avatar;
    /**
     * 电话
     */
    @TableField("phone_")
    @ApiModelProperty(value="电话")
    private String phone;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @TableField("email_")
    @ApiModelProperty(value="邮箱")
    private String email;
    /**
     * 身份证号码
     */
    @Pattern(regexp = "^(|\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message = "身份证格式不正确")
    @TableField("id_card")
    @ApiModelProperty(value="身份证号码")
    private String idCard;
    /**
     * 微信
     */
    @TableField("wei_xin")
    @ApiModelProperty(value="微信")
    private String weiXin;
    /**
     * 微博
     */
    @TableField("wei_bo")
    @ApiModelProperty(value="微博")
    private String weiBo;
    /**
     * QQ
     */
    @TableField("qq_")
    @ApiModelProperty(value="qq")
    private String qq;
    /**
     * 出生日期
     */
    @JSONField(format = "yyyy-MM-dd")
    @TableField("birth_day")
    @ApiModelProperty(value="出生日期")
    private Date birthDay;
    /**
     * 部门编号
     */
    @TableField("dept_id")
    @ApiModelProperty(value="部门id")
    private Long deptId;

    /**
     * 职位
     */
    @TableField("position_")
    @ApiModelProperty(value="职位")
    private String position;
    /**
     * 详细地址
     */
    @TableField("address_")
    @ApiModelProperty(value="详细地址")
    private String address;
    /**
     * 工号
     */
    @TableField("staff_no")
    @ApiModelProperty(value="工号")
    private String staffNo;
    /**
     * 角色
     */
    @TableField(exist = false)
    @ApiModelProperty(value="角色")
    private Long[] role;
    /**
     * 是否启用
     */
    @TableField("enable_")
    @ApiModelProperty(value="是否启用（1、启用；0、禁用）")
    private Integer enable;
    /**
     * 是否删除(0:未删除;1:已删除)
     */
    @TableField("is_del")
    @ApiModelProperty(value="是否删除(0:未删除;1:已删除)")
    private Integer isDel;
    /**
     * 备注
     */
    @TableField("remark_")
    @ApiModelProperty(value="备注")
    private String remark;
}
