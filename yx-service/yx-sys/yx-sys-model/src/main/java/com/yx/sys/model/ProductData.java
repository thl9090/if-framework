package com.yx.sys.model;

import com.yx.common.core.base.BaseModel;

public class ProductData extends BaseModel {

    private String productNo;//标的id
    private String title;//标的名称
    private Integer productStatus;//标的状态


    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }
}
