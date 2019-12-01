package com.yx.sys.rpc.api;

import com.yx.common.core.base.BaseService;
import com.yx.sys.model.product.ProductFile;

import java.util.List;

/**
 * <p>
 * 标的文件表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-31
 */
public interface ProductFileService extends BaseService<ProductFile> {

    /**
     * 根据标id查询标的文件
     * @param productId
     * @return
     */
    List<ProductFile> selectByProductId(Long productId);

    /**
     * 删除标的文件
     * @param productId
     * @return
     */
    int deleteByProductId(Long productId);
}
