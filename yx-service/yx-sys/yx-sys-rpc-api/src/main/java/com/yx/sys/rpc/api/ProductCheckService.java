package com.yx.sys.rpc.api;

import com.yx.common.core.base.BaseService;
import com.yx.sys.model.product.ProductCheck;
import com.yx.sys.model.vo.ProductCheckVO;

/**
 * <p>
 * 标的审核信息表 服务类
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-02
 */
public interface ProductCheckService extends BaseService<ProductCheck> {

    /**
     * 根据标id查询用户信息
     * @param productId
     * @param type
     * @return
     */
    ProductCheck selectByProductIdAndType(Long productId,Integer type);

    /**
     * 根据标id查询用户信息
     * @param productId
     * @param type
     * @return
     */
    ProductCheckVO selectVOByProductIdAndType(Long productId,Integer type);


}
