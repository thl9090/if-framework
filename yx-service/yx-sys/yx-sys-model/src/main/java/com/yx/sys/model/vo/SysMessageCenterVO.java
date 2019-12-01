package com.yx.sys.model.vo;

import com.yx.sys.model.SysMessageCenter;

/**
 * <p>
 * 消息中心表
 * </p>
 * @author TangHuaLiang
 * @since 2018-11-20
 */
public class SysMessageCenterVO extends SysMessageCenter {

    private static final long serialVersionUID = 1L;

    private String beforeTitle;
    private Long beforeId;
    private String nextTitle;
    private Long nextId;


	public String getBeforeTitle() {
		return beforeTitle;
	}

	public void setBeforeTitle(String beforeTitle) {
		this.beforeTitle = beforeTitle;
	}

	public Long getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(Long beforeId) {
		this.beforeId = beforeId;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public Long getNextId() {
		return nextId;
	}

	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}
}
