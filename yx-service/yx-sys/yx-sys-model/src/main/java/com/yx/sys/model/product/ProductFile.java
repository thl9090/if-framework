package com.yx.sys.model.product;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 标的文件表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-31
 */
@TableName("product_file")
public class ProductFile extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 标的id
     */
	@TableField("product_id")
	private Long productId;
    /**
     * 文件url
     */
	@TableField("url")
	private String url;


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ProductFile{" +
			", productId=" + productId +
			", url=" + url +
			"}";
	}
}
