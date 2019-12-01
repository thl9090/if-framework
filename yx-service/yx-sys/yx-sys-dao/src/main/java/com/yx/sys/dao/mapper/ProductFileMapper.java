package com.yx.sys.dao.mapper;

import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.product.ProductFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标的文件表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-31
 */
public interface ProductFileMapper extends BaseMapper<ProductFile> {

    /**
     * 根据标id查询标的文件
     * @param productId
     * @return
     */
    List<ProductFile> selectByProductId(@Param("productId")Long productId);

    /**
     * 删除标的文件，根据标的id
     * @param productId
     * @return
     */
    int deleteByProductId(@Param("productId")Long productId);

}
