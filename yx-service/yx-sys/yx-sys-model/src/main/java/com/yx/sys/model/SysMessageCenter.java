package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 消息中心表
 * </p>
 *
 * @author lilulu
 * @since 2018-08-15
 */
@TableName("sys_message_center")
public class SysMessageCenter extends BaseModel {

	private static final long serialVersionUID = 1L;

    /**
     * 内容标题
     */
	@TableField("title")
	private String title;
    /**
     * url路径
     */
	@TableField("url")
	private String url;
    /**
     * 内容描述
     */
	@TableField("content")
	private String content;
    /**
     * 本条数据的描述
     */
	@TableField("description")
	private String description;
	/**
	 * 页面标题
	 */
	@TableField("sketch")
	private String sketch;
    /**
     * 关键字
     */
	@TableField("keywords")
	private String keywords;
	/**
	 * 文件id
	 */
	@TableField("file_url")
	private String fileUrl;

	/**
	 * 1.公告列表2.视频专区3.行业质询4.媒体报道5.存管动态6.网贷知识7.政策法规
	 */
	@TableField("type")
	private Integer type;
	/**
	 * 内容是否有效，0-待发布 1-上线，2-下线
	 */
	@TableField("status")
	private Integer status;

	/**
	 * 置顶：0-不置顶 1,-置顶
	 */
	@TableField("ranking")
	private Integer ranking;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

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

	public String getSketch() {
		return sketch;
	}

	public void setSketch(String sketch) {
		this.sketch = sketch;
	}

	@Override
	public String toString() {
		return "SysMessageCenter{" +
				", title=" + title +
				", url=" + url +
				", content=" + content +
				", description=" + description +
				", sketch=" + sketch +
				", keywords=" + keywords +
				", fileUrl=" + fileUrl +
				", type=" + type +
				", status=" + status +
				"}";
	}
}
