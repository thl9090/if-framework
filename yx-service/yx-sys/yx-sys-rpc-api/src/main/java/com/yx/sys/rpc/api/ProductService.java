package com.yx.sys.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.base.BaseService;
import com.yx.common.core.exception.BusinessException;
import com.yx.sys.model.export.ProductExp;
import com.yx.sys.model.product.Product;
import com.yx.sys.model.vo.ProductVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 标的相关接口
 *
 * @author TangHuaLiang
 * @since 2018-7-30
 */
public interface ProductService extends BaseService<Product> {

    /**
     * 根据主键查询（主库）
     *
     * @param id
     * @return
     */
    Product getById(Long id);

    /**
     * 根据主键查询扩展查询（从库）
     *
     * @param id
     * @return
     */
    ProductVO selectVOById(Long id);

    /**
     * 根据标的编号查询
     *
     * @param productNo
     * @return
     */
    ProductVO selectVOByProductNo(String productNo);


    /**
     * 根据主键查询扩展查询（主库）
     *
     * @param id
     * @return
     */
    ProductVO getVOById(Long id);


    /**
     * 分页查询
     *
     * @param page
     * @param product
     * @return
     */
    Page<Product> selectPage(Page<Product> page, Product product);

    /**
     * 分页查询（扩展）
     *
     * @param page
     * @param productVO
     * @return
     */
    Page<ProductVO> selectVOPage(Page<ProductVO> page, ProductVO productVO);

    /**
     * 后台招标中列表
     *
     * @param page
     * @param productVO
     * @return
     */
    Page<ProductVO> selectBidingVOPage(Page<ProductVO> page, ProductVO productVO);

    /**
     * 投资专区列表
     *
     * @param page
     * @param productVO
     * @return
     */
    Page<ProductVO> selectBidPage(Page<ProductVO> page, ProductVO productVO);

    /**
     * 转让专区
     * @param page
     * @param productVO
     * @return
     */
    Page<ProductVO> selectTransferPage(Page<ProductVO> page, ProductVO productVO);



    /**
     * 标的列表查询
     *
     * @param productVO
     * @return
     */
    List<ProductVO> selectVOList(ProductVO productVO);

    /**
     * 根据id集合查询（从库）
     *
     * @param ids
     * @return
     */
    List<Product> selectByIds(List<Long> ids);

    /**
     * 根据id集合查询（主库）
     *
     * @param ids
     * @return
     */
    List<Product> getByIds(List<Long> ids);

    /**
     * 更新标的状态和投标金额
     *
     * @param id
     * @param bidAmount
     * @return
     */
    int updateBidAmountById(Long id, BigDecimal bidAmount) throws BusinessException;

    /**
     * 更新标的状态
     *
     * @param id
     * @param status
     * @return
     */
    int updateStatusById(Long id, Integer status);



    /**
     * 查询可以放款的标的信息
     *
     * @return
     */
    List<Long> queryForLoan();

    /**
     * 查询还款中的标的
     *
     * @return
     */
    List<Product> queryRepayingProduct();

    /**
     * 首页标的--新手标
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
     * PC-首页标的列表
     *
     * @return
     */
    List<ProductVO> selectIndexProductForPC();

    /**
     * EXCEL导出
     *
     * @param productVO
     * @return
     */
    List<ProductExp> selectExpList(ProductVO productVO);



    /**
     * 查询符合流标条件的标的
     *
     * @return
     */
    List<Product> selectListForFlowProduct();

    BigDecimal getTotalSuccessAmount();


    List<Long> getIdsByStatus(Integer productStatus);

    Integer getGuaranteeCountByGuaranteeUserId(Long userId);

    Product selectByTitle(String title);

    /**
     * 获取推广页标的信息
     *
     * @return
     */
    List<ProductVO> selectPromotionProduct();


    List<Product> selectByRootProductId(Long rootProductId, Integer productType, Integer productStatus);

    Integer getUnOverCountByUserId(Long userId);

    int queryCountByUserIdAndProductId(Long borrowUserId, Long productId);

    Integer selectCountByUserIdAndProductStatus(Long borrowUserId, Integer productStatus);

    Integer selectCountByUserIdAndNotProductStatus(Long borrowUserId, Integer productStatus);


    /**
     * 根据标的id查询标的名称
     *
     * @param productId
     * @return
     */
    String selectTitleById(Long productId);

    /**
     * 查询借款标满标数据
     *
     * @return
     */
    List<Long> selectFullproduct();

    /**
     * 根据标的id集合和状态查询标的数量
     *
     * @param productIds
     * @param productStatusList
     * @return
     */
    int getCountsByProductIdsAndStatusList(List<Long> productIds, List<Integer> productStatusList);

    /**
     * 根据标的id集合和状态查询标的id
     *
     * @param productIds
     * @param productStatus
     * @return
     */
    List<Long> getIdsByProductIdsAndStatus(List<Long> productIds, Integer productStatus);

}
