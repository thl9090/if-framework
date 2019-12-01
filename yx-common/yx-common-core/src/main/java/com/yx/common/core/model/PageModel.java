package com.yx.common.core.model;


import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseModel;

/**
 * 分页实体类
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
public class PageModel<T extends BaseModel> extends Page<T> {

    public PageModel() {
    }

    public PageModel(int current, int size) {
        super(current, size);
    }

    public PageModel(int current, int size, String orderByField) {
        super(current, size);
        super.setOrderByField(orderByField);
    }
}
