package com.yx.job.rpc.service.components.impl;

import com.yx.business.rpc.api.ProductBusinessService;
import com.yx.job.rpc.service.components.ProductComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author YanBingHao
 * @since 2018/8/23
 */
@Component
public class ProductComponentsImpl implements ProductComponents {

    private final Logger logger = LoggerFactory.getLogger(ProductComponentsImpl.class);

    @Autowired
    private ProductBusinessService productBusinessService;

    @Override
    public void payOffProduct() {
//        productBusinessService.payOffProduct(null);
    }

    @Override
    public void fullProductRemind() {
        try {
            productBusinessService.fullRemind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
