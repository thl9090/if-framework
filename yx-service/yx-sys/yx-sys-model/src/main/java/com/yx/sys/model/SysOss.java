package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 文件上传
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-29
 */
@TableName("sys_oss")
public class SysOss extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * URL地址
     */
	private String url;


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "SysOss{" +
			", url=" + url +
			"}";
	}
}
