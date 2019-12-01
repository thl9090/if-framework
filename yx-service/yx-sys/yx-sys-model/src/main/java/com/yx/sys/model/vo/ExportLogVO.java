package com.yx.sys.model.vo;

import com.yx.sys.model.ExportLog;

/**
 * <p>
 * 导出日志记录表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */

public class ExportLogVO extends ExportLog {

    private static final long serialVersionUID = 1L;

    private Integer currentPage;


	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
