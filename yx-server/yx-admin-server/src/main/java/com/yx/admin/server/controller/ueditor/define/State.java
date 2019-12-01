package com.yx.admin.server.controller.ueditor.define;

/**
 * 处理状态接口
 * @author TangHuaLiang
 *@since 2018-12-22
 */
public interface State {
	
	public boolean isSuccess();

	public void putInfo(String name, String val);

	public void putInfo(String name, long val);

	public String getInfoStr(String name);
	public Long getInfoLong(String name);

	public String toJSONString();

}
