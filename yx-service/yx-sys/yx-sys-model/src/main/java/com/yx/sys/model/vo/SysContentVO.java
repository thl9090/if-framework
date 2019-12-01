package com.yx.sys.model.vo;

import java.io.Serializable;

/**
 * 用户查询发展历程年份的接口
 * @author lilulu
 * @since 2018/10/26 17:44
 */
public class SysContentVO implements Serializable {

	private String year;
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
}
