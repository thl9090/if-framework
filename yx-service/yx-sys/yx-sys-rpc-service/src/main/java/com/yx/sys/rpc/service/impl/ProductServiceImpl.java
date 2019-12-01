package com.yx.sys.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.yx.common.core.base.BaseServiceImpl;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.pool.ThreadExecutor;
import com.yx.common.redis.util.CacheUtil;
import com.yx.sys.common.ProductAssetTypeEnum;
import com.yx.sys.common.ProductStatusEnum;
import com.yx.sys.common.ProductTypeEnum;
import com.yx.sys.dao.mapper.ProductCheckMapper;
import com.yx.sys.dao.mapper.ProductFileMapper;
import com.yx.sys.dao.mapper.ProductMapper;
import com.yx.sys.model.export.ProductExp;
import com.yx.sys.model.product.Product;
import com.yx.sys.model.vo.ProductVO;
import com.yx.sys.rpc.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.yx.sys.common.constant.ProductConstant.FULL_PRODUCT_WAIT_REMIND_PRODUCT_IDS_REDIS_KEY;

/**
 * 标的业务类
 *
 * @author TangHuaLiang
 * @since 2018-07-30
 */
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

    private static final List<Integer> PRODUCT_STATUS_LIST_FRO_LOAN = Lists.newArrayList();

    static {
        PRODUCT_STATUS_LIST_FRO_LOAN.add(ProductStatusEnum.FULL_PRODUCT.getStatus());
        PRODUCT_STATUS_LIST_FRO_LOAN.add(ProductStatusEnum.BANK_LOANFAIL.getStatus());
    }

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductFileMapper productFileMapper;
    @Autowired
    private ProductCheckMapper productCheckMapper;
    @Autowired
    private ThreadExecutor threadExecutor;

    @Override
    public Product getById(Long id) {
        return productMapper.getById(id);
    }

    @Override
    public ProductVO selectVOById(Long id) {
        return productMapper.selectVOById(id);
    }

    @Override
    public ProductVO selectVOByProductNo(String productNo) {
        return productMapper.selectVOByProductNo(productNo);
    }

    @Override
    public ProductVO getVOById(Long id) {
        return productMapper.getVOById(id);
    }


    @Override
    public Page<Product> selectPage(Page<Product> page, Product product) {
        List<Product> list = productMapper.selectPage(page, product);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<ProductVO> selectVOPage(Page<ProductVO> page, ProductVO productVO) {
        List<ProductVO> list = productMapper.selectVOPage(page, productVO);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<ProductVO> selectBidingVOPage(Page<ProductVO> page, ProductVO productVO) {
        List<ProductVO> list = productMapper.selectBidingVOPage(page, productVO);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<ProductVO> selectBidPage(Page<ProductVO> page, ProductVO productVO) {
        List<ProductVO> list = productMapper.selectBidPage(page, productVO);
        page.setRecords(list);
        return page;
    }

    @Override
    public Page<ProductVO> selectTransferPage(Page<ProductVO> page, ProductVO productVO) {
        List<ProductVO> list = productMapper.selectTransferPage(page, productVO);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ProductVO> selectVOList(ProductVO productVO) {
        return productMapper.selectVOList(productVO);
    }

    @Override
    public List<Product> selectByIds(List<Long> ids) {
        return productMapper.selectByIds(ids);
    }

    @Override
    public List<Product> getByIds(List<Long> ids) {
        return productMapper.getByIds(ids);
    }

    @Override
    public int updateBidAmountById(Long id, BigDecimal bidAmount) throws BusinessException {

        Product product = productMapper.getById(id);
        if (product == null) {
            throw new BusinessException(String.format("未查询到id为：【%s】的product信息！", id));
        }
        Integer status = ProductStatusEnum.BIDING.getStatus();
        BigDecimal allBidAmount = bidAmount.add(product.getBidAmount());
        if (allBidAmount.compareTo(product.getProductAmount()) >= 0) {
            status = ProductStatusEnum.FULL_PRODUCT.getStatus();
            // 将满标的标的id放入待提醒列表
            threadExecutor.getPool().execute(() -> {
                if (ProductTypeEnum.LOAN.getStatus() == product.getProductType().intValue()) {
                    CacheUtil.getCache().rpush(FULL_PRODUCT_WAIT_REMIND_PRODUCT_IDS_REDIS_KEY, id);
                }
            });
        }

        return productMapper.updateStatusAndBidAmountById(id, allBidAmount, status);
    }

    @Override
    public int updateStatusById(Long id, Integer status) {
        return productMapper.updateStatusById(id, status);
    }


    @Override
    public List<Long> queryForLoan() {
        return productMapper.selectForLoan();
    }

    @Override
    public List<Product> queryRepayingProduct() {
        return productMapper.selectByStatusList(Lists.newArrayList(ProductStatusEnum.REPAYMENTING.getStatus()));
    }

    @Override
    public ProductVO selectIndexNewUserProduct() {
        return productMapper.selectIndexNewUserProduct();
    }

    @Override
    public List<ProductVO> selectIndexProduct() {
        return productMapper.selectIndexProduct();
    }

    @Override
    public List<ProductVO> selectIndexProductForPC() {
        //福享系列
        ProductVO productVO = productMapper.selectIndexProductForPCByAssetType(ProductAssetTypeEnum.FU_XIANG.getStatus());
        //安享系列
        ProductVO productVO1 = productMapper.selectIndexProductForPCByAssetType(ProductAssetTypeEnum.AN_XIANG.getStatus());
        //私享系列
        ProductVO productVO2 = productMapper.selectIndexProductForPCByAssetType(ProductAssetTypeEnum.SI_XIANG.getStatus());
        //尊享系列
        ProductVO productVO3 = productMapper.selectIndexProductForPCByAssetType(ProductAssetTypeEnum.ZUN_XIANG.getStatus());
        List<ProductVO> list = new ArrayList<>();
        if (productVO != null) {
            list.add(productVO);
        }
        if (productVO1 != null) {
            list.add(productVO1);
        }
        if (productVO2 != null) {
            list.add(productVO2);
        }
        if (productVO3 != null) {
            list.add(productVO3);
        }

        return list;
    }

    @Override
    public List<ProductExp> selectExpList(ProductVO productVO) {
        return productMapper.selectExpList(productVO);
    }


    @Override
    public List<Product> selectListForFlowProduct() {
        return productMapper.selectListForFlowProduct();
    }

    @Override
    public BigDecimal getTotalSuccessAmount() {
        return productMapper.getTotalSuccessAmount();
    }


    @Override
    public List<Long> getIdsByStatus(Integer productStatus) {
        return productMapper.getIdsByStatus(productStatus);
    }

    @Override
    public Integer getGuaranteeCountByGuaranteeUserId(Long userId) {
        return productMapper.getGuaranteeCountByGuaranteeUserId(userId);
    }

    @Override
    public Product selectByTitle(String title) {
        return productMapper.selectByTitle(title);
    }

    @Override
    public List<ProductVO> selectPromotionProduct() {
        List<ProductVO> list = productMapper.selectPromotionProduct();
        for (ProductVO pro : list) {
            pro.setAssetTypeMiniName(ProductAssetTypeEnum.resolve(pro.getAssetType()).getDesc());
        }
        return list;
    }

    @Override
    public List<Product> selectByRootProductId(Long rootProductId, Integer productType, Integer productStatus) {
        return productMapper.selectByRootProductId(rootProductId, productType, productStatus);
    }

    @Override
    public Integer getUnOverCountByUserId(Long userId) {
        return productMapper.getUnOverCountByUserId(userId);
    }

    @Override
    public int queryCountByUserIdAndProductId(Long borrowUserId, Long productId) {
        return productMapper.selectCountByUserIdAndProductId(borrowUserId, productId);
    }

    @Override
    public Integer selectCountByUserIdAndProductStatus(Long borrowUserId, Integer productStatus) {
        return productMapper.selectCountByUserIdAndProductStatus(borrowUserId, productStatus);
    }

    @Override
    public Integer selectCountByUserIdAndNotProductStatus(Long borrowUserId, Integer productStatus) {
        return productMapper.selectCountByUserIdAndNotProductStatus(borrowUserId, productStatus);
    }

    @Override
    public String selectTitleById(Long productId) {
        return productMapper.selectTitleById(productId);
    }

    @Override
    public List<Long> selectFullproduct() {
        return productMapper.selectFullproduct();
    }

    @Override
    public int getCountsByProductIdsAndStatusList(List<Long> productIds, List<Integer> productStatusList) {
        return productMapper.getCountsByProductIdsAndStatusList(productIds, productStatusList);
    }

    @Override
    public List<Long> getIdsByProductIdsAndStatus(List<Long> productIds, Integer productStatus) {
        return productMapper.getIdsByProductIdsAndStatus(productIds, productStatus);
    }

}
