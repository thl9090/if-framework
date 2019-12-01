package com.yx.sys.model.vo;

import com.yx.sys.model.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-01
 */
@Data
@ApiModel(value="SysUserVO",description="系统用户扩展类")
public class SysUserVO extends SysUser {

    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    @ApiModelProperty(value="部门名称")
    private String deptName;

    @ApiModelProperty(value="角色名称")
    private String roleNames;

    private String ip;

    private String sessionId;

    private List<Long> userIdList;



}
