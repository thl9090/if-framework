package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author lilulu
 * @since 2018-08-09
 */
@TableName("sys_slideshow")
public class SysSlideshow extends BaseModel {

    private static final long serialVersionUID = 1L;

	@TableField("type")
	private Integer type;
    /**
     * 内容是否有效，0-待发布，1-发布，2-下线
     */
	@TableField("status")
	private Integer status;
    /**
     * 点击图片后进入那个网址
     */
	@TableField("url")
	private String url;
    /**
     * 内容标题
     */
	@TableField("title")
	private String title;
    /**
     * 图片的路径
     */
	@TableField("file_path")
	private String filePath;
    /**
     * 客户端:1、pc；2、web；3、mobile；4、wx
     */
	@TableField("client_type")
	private Integer clientType;

	@TableField("start_time")
	private Date startTime;

	@TableField("end_time")
	private Date endTime;

	@TableField("before_url")
	private String beforeUrl;


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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "SysSlideshow{" +
			", type=" + type +
			", status=" + status +
			", url=" + url +
			", title=" + title +
			", filePath=" + filePath +
			", clientType=" + clientType +
			"}";
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBeforeUrl() {
		return beforeUrl;
	}

	public void setBeforeUrl(String beforeUrl) {
		this.beforeUrl = beforeUrl;
	}
}
