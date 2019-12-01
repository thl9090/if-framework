package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *  代码示例
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-13
 */
@TableName("code_demo")
public class CodeDemo extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
	@ApiModelProperty(value="系统用户id")
	@TableField("sys_user_id")
	private Long sysUserId;
    /**
     * 姓名
     */
	@ApiModelProperty(value="姓名")
	@TableField("name")
	private String name;


	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CodeDemo{" +
			", sysUserId=" + sysUserId +
			", name=" + name +
			"}";
	}
}
