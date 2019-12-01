package com.yx.sys.model.product;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yx.common.core.base.BaseModel;

/**
 * <p>
 * 标的审核信息表
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-02
 */
@TableName("product_check")
public class ProductCheck extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 标的id
     */
	@TableField("product_id")
	private Long productId;

	/**
	 * 类型：1、散标；2、智投
	 */
	@TableField("type")
	private Integer type;
    /**
     * 初审状态：1、审核通过；0、不通过
     */
	@TableField("first_check_status")
	private Integer firstCheckStatus;
    /**
     * 初审描述
     */
	@TableField("first_check_remark")
	private String firstCheckRemark;
    /**
     * 终审状态
     */
	@TableField("final_check_status")
	private Integer finalCheckStatus;
    /**
     * 终审描述
     */
	@TableField("final_check_remark")
	private String finalCheckRemark;


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getFirstCheckStatus() {
		return firstCheckStatus;
	}

	public void setFirstCheckStatus(Integer firstCheckStatus) {
		this.firstCheckStatus = firstCheckStatus;
	}

	public String getFirstCheckRemark() {
		return firstCheckRemark;
	}

	public void setFirstCheckRemark(String firstCheckRemark) {
		this.firstCheckRemark = firstCheckRemark;
	}

	public Integer getFinalCheckStatus() {
		return finalCheckStatus;
	}

	public void setFinalCheckStatus(Integer finalCheckStatus) {
		this.finalCheckStatus = finalCheckStatus;
	}

	public String getFinalCheckRemark() {
		return finalCheckRemark;
	}

	public void setFinalCheckRemark(String finalCheckRemark) {
		this.finalCheckRemark = finalCheckRemark;
	}

	@Override
	public String toString() {
		return "ProductCheck{" +
			", productId=" + productId +
			", firstCheckStatus=" + firstCheckStatus +
			", firstCheckRemark=" + firstCheckRemark +
			", finalCheckStatus=" + finalCheckStatus +
			", finalCheckRemark=" + finalCheckRemark +
			"}";
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
