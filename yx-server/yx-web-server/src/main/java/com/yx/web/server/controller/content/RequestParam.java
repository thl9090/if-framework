package com.yx.web.server.controller.content;

import com.yx.common.core.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用于分页查询
 *
 * @author lilulu
 * @since 2018/08/17 10:12
 */
public class RequestParam extends BaseModel {

	@ApiModelProperty(value = "当前页码")
    private Integer currentPage;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
