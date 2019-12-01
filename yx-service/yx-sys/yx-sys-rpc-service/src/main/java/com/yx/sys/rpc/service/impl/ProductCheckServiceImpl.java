package com.yx.sys.rpc.service.impl;

import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.ProductCheckMapper;
import com.yx.sys.model.product.ProductCheck;
import com.yx.sys.model.vo.ProductCheckVO;
import com.yx.sys.rpc.api.ProductCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标的审核信息表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-02
 */
@Service("productCheckService")
public class ProductCheckServiceImpl extends BaseServiceImpl<ProductCheckMapper, ProductCheck> implements ProductCheckService {

    @Autowired
    private ProductCheckMapper productCheckMapper;

    @Override
    public ProductCheck selectByProductIdAndType(Long productId,Integer type) {
        return productCheckMapper.selectByProductIdAndType(productId,type);
    }

    @Override
    public ProductCheckVO selectVOByProductIdAndType(Long productId,Integer type) {
        return productCheckMapper.selectVOByProductIdAndType(productId,type);
    }
}
