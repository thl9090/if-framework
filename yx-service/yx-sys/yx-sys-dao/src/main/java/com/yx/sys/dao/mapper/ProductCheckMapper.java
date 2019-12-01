package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.product.ProductCheck;
import com.yx.sys.model.vo.ProductCheckVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 标的审核信息表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-02
 */
public interface ProductCheckMapper extends BaseMapper<ProductCheck> {

    ProductCheck selectByProductIdAndType(@Param("productId")Long productId,@Param("type")Integer type);

    ProductCheckVO selectVOByProductIdAndType(@Param("productId")Long productId,@Param("type")Integer type);

}
