package com.yx.sys.dao.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseMapper;
import com.yx.sys.model.export.ProductExp;
import com.yx.sys.model.product.Product;
import com.yx.sys.model.vo.ProductVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 标的信息表 Mapper 接口
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-07-20
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 根据主键查询（主库）
     *
     * @param id
     * @return
     */
    Product getById(@Param("id") Long id);

    /**
     * 根据主键查询扩展查询（从库）
     *
     * @param id
     * @return
     */
    ProductVO selectVOById(@Param("id") Long id);

    /**
     * 根据标的编号查询（从库）
     *
     * @param productNo
     * @return
     */
    ProductVO selectVOByProductNo(@Param("productNo") String productNo);

    /**
     * 根据主键查询扩展查询（主库）
     *
     * @param id
     * @return
     */
    ProductVO getVOById(@Param("id") Long id);

    /**
     * 分页查询
     *
     * @param page
     * @param product
     * @return
     */
    List<Product> selectPage(Page<Product> page, @Param("product") Product product);

    /**
     * 分页查询扩展
     *
     * @param page
     * @param productVO
     * @return
     */
    List<ProductVO> selectVOPage(Page<ProductVO> page, @Param("productVO") ProductVO productVO);

    /**
     * 后台admin查询招标中列表
     *
     * @param page
     * @param productVO
     * @return
     */
    List<ProductVO> selectBidingVOPage(Page<ProductVO> page, @Param("productVO") ProductVO productVO);


    /**
     * 投资专区列表
     *
     * @param page
     * @param productVO
     * @return
     */
    List<ProductVO> selectBidPage(Page<ProductVO> page, @Param("productVO") ProductVO productVO);

    /**
     * 转让专区列表查询
     * @param page
     * @param productVO
     * @return
     */
    List<ProductVO> selectTransferPage(Page<ProductVO> page, @Param("productVO") ProductVO productVO);


    /**
     * 标的列表查询
     *
     * @param productVO
     * @return
     */
    List<ProductVO> selectVOList(@Param("productVO") ProductVO productVO);

    /**
     * 根据id集合查询（从库）
     *
     * @param ids
     * @return
     */
    List<Product> selectByIds(@Param("ids") List<Long> ids);

    /**
     * 根据id集合查询（主库）
     *
     * @param ids
     * @return
     */
    List<Product> getByIds(@Param("ids") List<Long> ids);

    /**
     * 更新标的状态和投标金额
     *
     * @param id
     * @param bidAmount
     * @param status
     * @return
     */
    int updateStatusAndBidAmountById(@Param("id") Long id, @Param("bidAmount") BigDecimal bidAmount, @Param("status") Integer status);

    /**
     * 更新标的状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新债转标的状态
     *
     * @param rootProductId
     * @param status
     * @return
     */
    int updateStatusByRootProductId(@Param("rootProductId") Long rootProductId, @Param("status") Integer status);


    List<Product> selectByStatusList(@Param("statusList") List<Integer> statusList);

    List<Long> selectForLoan();

    /**
     * 首页标的列表--新手标
     *
     * @return
     */
    ProductVO selectIndexNewUserProduct();

    /**
     * 首页标的列表--优选项目
     *
     * @return
     */
    List<ProductVO> selectIndexProduct();

    /**
     * PC首页标：福享、安享、思享、尊享
     *
     * @param assetType
     * @return
     */
    ProductVO selectIndexProductForPCByAssetType(@Param("assetType") Integer assetType);

    /**
     * 查询EXCEL导出数据
     *
     * @param productVO
     * @return
     */
    List<ProductExp> selectExpList(@Param("productVO") ProductVO productVO);


    /**
     * 查询符合流标条件的标的
     *
     * @return
     */
    List<Product> selectListForFlowProduct();

    BigDecimal getTotalSuccessAmount();



    List<Long> getIdsByStatus(@Param("status") Integer status);

    Integer getGuaranteeCountByGuaranteeUserId(Long guaranteeUserId);


    Product selectByTitle(@Param("title") String title);

    List<ProductVO> selectPromotionProduct();

    List<Product> selectByRootProductId(@Param("rootProductId") Long rootProductId, @Param("productType") Integer productType, @Param("productStatus") Integer productStatus);

    Integer getUnOverCountByUserId(@Param("userId") Long userId);

    int selectCountByUserIdAndProductId(@Param("borrowUserId") Long borrowUserId, @Param("productId") Long productId);

    Integer selectCountByUserIdAndProductStatus(@Param("borrowUserId") Long borrowUserId, @Param("productStatus") Integer productStatus);

    Integer selectCountByUserIdAndNotProductStatus(@Param("borrowUserId") Long borrowUserId, @Param("productStatus") Integer productStatus);


    String selectTitleById(@Param("productId") Long productId);


    List<Long> selectFullproduct();

    int getCountsByProductIdsAndStatusList(@Param("productIds") List<Long> productIds, @Param("productStatusList") List<Integer> productStatusList);

    List<Long> getIdsByProductIdsAndStatus(@Param("productIds") List<Long> productIds, @Param("productStatus") Integer productStatus);

}
