package com.yx.sys.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

import java.util.Date;

/**
 * <p>
 * 内容标
 * </p>
 *
 * @author lilulu
 * @since 2018-08-20
 */
@TableName("sys_content")
public class SysContent extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 板块id（ContentModelTypeEnum.java中有详细解释）
	 */
	@TableField("model_type")
	private Integer modelType;
	/**
	 * 内容是否有效，0-待发布 1-上线，2-下线
	 */
	@TableField("status")
	private Integer status;
	/**
	 * url路径(http://XXX/XX/X）
	 */
	@TableField("url")
	private String url;
	/**
	 * 内容标题
	 */
	@TableField("title")
	private String title;
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
	@TableField("model_time")
	private Date modelTime;
	/**
	 * 关键字
	 */
	@TableField("keywords")
	private String keywords;

    /**
     * 该字段是类型中又一个类型
     * 用于每种model_type中的类型中的详细类型
     * 如：1、服务协议，服务协议中有注册服务协议、和投资、借款协议
     * 则枚举为：
     *        XXXX(1,"服务协议"),
     *        XXXX(2,"投资协议"),
     *        XXXX(3,"借款协议");
     * 如果model_type值为1，该字段为1，则是服务协议。
     * 枚举类的命名规则为:该数据的model_type对应枚举的 参数名+types.java
     */
	@TableField("model_type_types")
	private Integer modelTypeTypes;

    /**
     * 客户端类型，枚举中有详细介绍
     */
	@TableField("client_type")
	private Integer clientType;


	public Integer getModelType() {
		return modelType;
	}

	public void setModelType(Integer modelType) {
		this.modelType = modelType;
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

	@Override
	public String toString() {
		return "SysContent{" +
				", modelType=" + modelType +
				", status=" + status +
				", url=" + url +
				", title=" + title +
				", content=" + content +
				", description=" + description +
				", modelTime=" + modelTime +
				", keywords=" + keywords +
				", modelTypeTypes=" + modelTypeTypes +
				"}";
	}

    public Integer getModelTypeTypes() {
        return modelTypeTypes;
    }

    public void setModelTypeTypes(Integer modelTypeTypes) {
        this.modelTypeTypes = modelTypeTypes;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Date getModelTime() {
        return modelTime;
    }

    public void setModelTime(Date modelTime) {
        this.modelTime = modelTime;
    }
}
