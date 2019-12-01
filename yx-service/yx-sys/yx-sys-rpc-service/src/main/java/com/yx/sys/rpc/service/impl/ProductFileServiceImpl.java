package com.yx.sys.rpc.service.impl;

import com.yx.common.core.base.BaseServiceImpl;
import com.yx.sys.dao.mapper.ProductFileMapper;
import com.yx.sys.model.product.ProductFile;
import com.yx.sys.rpc.api.ProductFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标的文件表 服务实现类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-31
 */
@Service("productFileService")
public class ProductFileServiceImpl extends BaseServiceImpl<ProductFileMapper, ProductFile> implements ProductFileService {

    @Autowired
    private ProductFileMapper productFileMapper;

    @Override
    public List<ProductFile> selectByProductId(Long productId) {
        return productFileMapper.selectByProductId(productId);
    }

    @Override
    public int deleteByProductId(Long productId) {
        return productFileMapper.deleteByProductId(productId);
    }
}
