package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 导出日志记录表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-13
 */
@TableName("export_log")
public class ExportLog extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 导出类型：1、用户信息导出；2、标的信息导出（具体看枚举定义）
     */
	@TableField("type")
	private Integer type;
    /**
     * 状态：0、处理中；1、已完成
     */
	@TableField("status")
	private Integer status;
    /**
     * 文件的url
     */
	@TableField("file_url")
	private String fileUrl;
    /**
     * 耗时（单位：毫秒）
     */
	@TableField("times")
	private Long times;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getTimes() {
		return times;
	}

	public void setTimes(Long times) {
		this.times = times;
	}

	@Override
	public String toString() {
		return "ExportLog{" +
			", type=" + type +
			", status=" + status +
			", fileUrl=" + fileUrl +
			", times=" + times +
			"}";
	}
}
