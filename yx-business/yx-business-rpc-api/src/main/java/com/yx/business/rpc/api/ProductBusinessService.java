package com.yx.business.rpc.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.sys.model.vo.ProductVO;

/**
 * 标的业务组装类
 *
 * @author TangHuaLiang
 * @since 2018-08-05
 */
public interface ProductBusinessService {

    /*
     * 添加标的
     * @param productVO
     */
    void addProduct(ProductVO productVO) throws Exception;

    /**
     * 修改标的
     *
     * @param productVO
     * @throws Exception
     */
    void updateProduct(ProductVO productVO) throws Exception;

    /**
     * 标的初审
     *
     * @param productVO
     */
    void firstCheck(ProductVO productVO) throws Exception;

    /**
     * 标的终审
     *
     * @param productVO
     */
    void finalCheck(ProductVO productVO) throws Exception;


    /**
     * 根据投标记录
     * @param bidOrderId
     * @throws Exception
     */
    void flowProduct(Long bidOrderId) throws Exception;


    /**
     * 定时任务查询正在处理中的流标记录
     *
     * @param productId
     */
    void flowProductRecordProcess(Long productId) throws Exception;

    /**
     * 获取标的名称，根据标的借款类型
     *
     * @param assetType
     * @return
     * @throws Exception
     */
    String getProductTitle(Integer assetType) throws Exception;



    /**
     * 新增放款成功提醒的手机号
     *
     * @param phone
     */
    void addRemindPhone(String phone) throws Exception;

    /**
     * 修稿放款成功提醒的手机号
     *
     * @param oldPhone
     * @param newPhone
     * @throws Exception
     */
    void editRemindPhone(String oldPhone, String newPhone) throws Exception;

    /**
     * 获取
     *
     * @return
     * @throws Exception
     */
    Page pageRemindPhones(Page page) throws Exception;

    /**
     * 删除提醒手机号
     *
     * @param phone
     * @throws Exception
     */
    void delRemindPhone(String phone) throws Exception;

    /**
     * 放款成功的提醒
     *
     * @throws Exception
     */
    void fullRemind() throws Exception;

    /**
     * 满标放款
     *
     * @throws Exception
     */
    void fullLoan(Long productId) throws Exception;

}
