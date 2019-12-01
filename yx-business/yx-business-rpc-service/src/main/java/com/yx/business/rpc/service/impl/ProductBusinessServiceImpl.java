package com.yx.business.rpc.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.business.rpc.api.ProductBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.enums.YesOrNoEnum;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.UUIDUtil;
import com.yx.sys.common.*;
import com.yx.sys.model.product.Product;
import com.yx.sys.model.product.ProductCheck;
import com.yx.sys.model.product.ProductFile;
import com.yx.sys.model.userMessageLog.UserMessageLog;
import com.yx.sys.model.vo.ProductVO;
import com.yx.sys.rpc.api.ProductCheckService;
import com.yx.sys.rpc.api.ProductFileService;
import com.yx.sys.rpc.api.ProductService;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.user.common.enums.CustTypeEnum;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.rpc.api.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yx.business.common.constant.BidConstant.BID_SINGLE_AMOUNT_KEY;


/**
 * 标的业务组装类
 *
 * @author TangHuaLiang
 * @since 2019-03-12
 */
@Slf4j
@Service("productBusinessService")
public class ProductBusinessServiceImpl implements ProductBusinessService {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductFileService productFileService;
    @Autowired
    private ProductCheckService productCheckService;
    @Autowired
    private UserInfoService userInfoService;
//    @Autowired
//    private FlowProductService flowProductService;
//    @Autowired
//    private BidService bidService;
//    @Autowired
//    private RepaymentService repaymentService;
//    @Autowired
//    private UserMessageLogService userMessageLogService;
//    @Autowired
//    private ReceiptTransferService receiptTransferService;
//    @Autowired
//    private ETKProductManagementServcie etkProductManagementServcie;
//    @Autowired
//    private AuthService authService;
    @Autowired
    private SysParamService sysParamService;
//    @Autowired
//    private SysSmsService sysSmsService;
//    @Autowired
//    private CompanyLegalInfoService companyLegalInfoService;
//    @Autowired
//    private FddUserService fddUserService;
//    @Autowired
//    private PlanService planService;
//    @Autowired
//    private AgreementService agreementService;
//    @Autowired
//    private BidComponents bidComponents;
//    @Autowired
//    private BusinessCommonComponents businessCommonComponents;
//    @Autowired
//    private SmartBidProductRelaService smartBidProductRelaService;
//    @Autowired
//    private SmartBidMatchComponents smartBidMatchComponents;
//    @Autowired
//    private BidOrderService bidOrderService;


    /**
     * 流标成功后接收短信的人员的名单
     */
    private static String FLOW_PRODUCT_SMS_PHONE = "FLOW_PRODUCT_SMS_PHONE";


    @Override
    public void addProduct(ProductVO productVO) throws Exception {

        if (productVO == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请求参数不能为空");
        }

        if (productVO.getUseType() == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请求参数借款用途不能为空");
        }
        if (productVO.getUseType().equals(ProductUseTypeEnum.OTHER.getStatus()) && StringUtils.isBlank(productVO.getUseTypeRemark())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "当借款用途类型为其它时，借款用途备注不能为空");
        }

        if (productVO.getUseType().equals(ProductUseTypeEnum.OTHER.getStatus()) && productVO.getUseTypeRemark().length() > 20) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "当借款用途类型为其它时，借款用途备注字数小于21个");
        }

        if (productVO.getBorrowUserId() == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请求参数BorrowUserId不能为空");
        }

        UserInfoVO userInfoVO = userInfoService.selectVOById(productVO.getBorrowUserId());
        if (userInfoVO == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "该借款人用户不存在");
        }
        //判断借款人有没有通过法大大实名认证
//        FddUser fddUser = null;
////                fddUserService.selectByUserIdAndType(userInfoVO.getId(), FddTypeEnum.TYPE2.getId());
//        if (fddUser == null) {
//            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "借款人未完成法大大实名认证");
//        }
//        if (fddUser.getStatus() == FddStatusEnum.STEP_2.getId()) {
//            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "借款人未完成法大大实名证书申请");
//        }
//        if (fddUser.getStatus() != FddStatusEnum.STEP_2.getId() && fddUser.getStatus() != FddStatusEnum.STEP_3.getId()) {
//            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "借款人未完成法大大实名认证");
//        }


        if (productVO.getGuaranteeId() == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "参数借GuaranteeId借款推荐方不能为空");
        }
        UserInfo guaranteeUserInfo = userInfoService.selectByGuaranteeId(productVO.getGuaranteeId());
        if (guaranteeUserInfo == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有查询到有效的担保方信息！");
        }
        //添加企业垫付账户id
        productVO.setAdvancePaymentUserId(guaranteeUserInfo.getId());

        if (productVO.getProductAmount() == null) {
            throw new BusinessException("请填写借款金额");
        }
        if (productVO.getMinBidAmount() == null) {
            throw new BusinessException("请填写最小出借金额");
        }
        if (productVO.getMaxBidAmount() == null) {
            throw new BusinessException("请填写最大出借金额");
        }
        if (productVO.getMinBidAmount().compareTo(productVO.getMaxBidAmount()) > 0) {
            throw new BusinessException("最小出借金额不能大于最大出借金额");
        }


        BigDecimal minBidAmount = sysParamService.queryBigDecimalByKey(BID_SINGLE_AMOUNT_KEY);
        if (minBidAmount == null) {
            throw new BusinessException("系统未设置出借单笔最小金额");
        }
        if (productVO.getMinBidAmount().compareTo(minBidAmount) < 0) {
            throw new BusinessException(String.format("最小出借金额不能小于系统设置出借单笔最小金额%s元", minBidAmount));
        }

        BigDecimal[] divideAndRemainder = productVO.getProductAmount().divideAndRemainder(minBidAmount);
        if (BigDecimal.ZERO.compareTo(divideAndRemainder[1]) != 0) {
            throw new BusinessException(String.format("借款金额必须是系统设置最小出借金额(%s)的整数倍", minBidAmount));
        }

        BigDecimal[] divideAndRemainderOne = productVO.getMinBidAmount().divideAndRemainder(minBidAmount);
        if (BigDecimal.ZERO.compareTo(divideAndRemainderOne[1]) != 0) {
            throw new BusinessException(String.format("最小出借金额必须是系统设置最小出借金额(%s)的整数倍", minBidAmount));
        }
        if (productVO.getMaxBidAmount().compareTo(productVO.getMinBidAmount()) < 0) {
            throw new BusinessException("最大出借金额不能小于最小出借金额");
        }
        if (productVO.getMaxBidAmount().compareTo(productVO.getProductAmount()) > 0) {
            throw new BusinessException("最大出借金额不能大于借款金额");
        }

        BigDecimal[] divideAndRemainderTwo = productVO.getMaxBidAmount().divideAndRemainder(minBidAmount);
        if (BigDecimal.ZERO.compareTo(divideAndRemainderTwo[1]) != 0) {
            throw new BusinessException(String.format("最大投标金额必须是系统设置最小出借金额(%s)的整数倍", minBidAmount));
        }

        if (YesOrNoEnum.YES.getId().equals(productVO.getIsAddProfit())) {
            if (BigDecimal.ZERO.compareTo(productVO.getAddProfit()) >= 0) {
                throw new BusinessException("加息利率必须大于0！");
            }
        }

        if (YesOrNoEnum.YES.getId().equals(productVO.getIsJoinSmart())) {
            if (productVO.getSmartBidPlanId() == null) {
                throw new BusinessException("没有获取到智投计划的id！");
            }
        }



        if (StringUtils.isBlank(userInfoVO.getEmail())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "借款人未绑定邮箱，不能发标");
        }

        if (!(userInfoVO.getCustType().equals(CustTypeEnum.PERSONAL_BORROWER.getStatus()) || userInfoVO.getCustType().equals(CustTypeEnum.COMPANY_BORROWER.getStatus()))) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "该借款人不是融资用户类型不能发布借款标");
        }

        if (userInfoVO.getCustType().equals(CustTypeEnum.PERSONAL_BORROWER.getStatus())) {
            productVO.setIsCompany(0);
        } else if (userInfoVO.getCustType().equals(CustTypeEnum.COMPANY_BORROWER.getStatus())) {
            productVO.setIsCompany(1);
        } else {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "该借款人不是融资用户类型不能发布借款标");
        }

        //招标天数不能超过20天
        if (productVO.getTimeCount() == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到招标天数");
        }
        if (productVO.getTimeCount() > 20 || productVO.getTimeCount() < 1) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "招标天数必须大于0并且不能超过20天");
        }

        //判断用户是否开通自动还款授权

        List<Long> oriBorrowerUserIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(productVO.getOriBorrowerIds())) {
            String[] split = productVO.getOriBorrowerIds().split(",");
            for (int i = 0; i < split.length; i++) {
                if (StringUtils.isNotBlank(split[i]) &&
                        !oriBorrowerUserIdList.contains(Long.valueOf(split[i]))) {

                    oriBorrowerUserIdList.add(Long.valueOf(split[i]));
                }
            }
        }
        if (CollectionUtils.isEmpty(productVO.getFileList())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请上传借款材料");
        }


        String productTitle = getProductTitle(productVO.getAssetType());
        productVO.setTitle(productTitle);
        productVO.setProductNo(UUIDUtil.getUUID());

        //标地类型：借款标
        productVO.setProductType(ProductTypeEnum.LOAN.getStatus());
        //标地状态：初审
        productVO.setProductStatus(ProductStatusEnum.FIRST_AUDIT.getStatus());
        productVO.setCreateTime(new Date());
        productVO.setUpdateTime(new Date());
        productVO.setSource(ProductSourceEnum.ADMIN_ADD.getStatus());
        //1、保存标的信息
        Product add = productService.add(productVO);

        //2、保存标的文件
        if (CollectionUtils.isNotEmpty(productVO.getFileList())) {
            for (String url : productVO.getFileList()) {
                if (StringUtils.isNotBlank(url)) {
                    ProductFile file = new ProductFile();
                    file.setProductId(add.getId());
                    file.setUrl(url);
                    file.setCreateBy(productVO.getCreateBy());
                    file.setCreateTime(new Date());
                    file.setUpdateBy(productVO.getCreateBy());
                    file.setUpdateTime(new Date());
                    productFileService.insert(file);
                }
            }
        }
    }

    @Override
    public void updateProduct(ProductVO productVO) throws Exception {
        List<Long> oriBorrowerUserIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(productVO.getOriBorrowerIds())) {
            String[] split = productVO.getOriBorrowerIds().split(",");
            for (int i = 0; i < split.length; i++) {
                if (StringUtils.isNotBlank(split[i]) &&
                        !oriBorrowerUserIdList.contains(Long.valueOf(split[i]))) {

                    oriBorrowerUserIdList.add(Long.valueOf(split[i]));
                }
            }
        }
        if (CollectionUtils.isEmpty(productVO.getFileList())) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请上传借款材料");
        }

        //1、更新标的信息
        productVO.setUpdateTime(new Date());
        productService.updateById(productVO);

        //2、更新标的文件
        productFileService.deleteByProductId(productVO.getId());
        if (CollectionUtils.isNotEmpty(productVO.getFileList())) {
            for (String url : productVO.getFileList()) {
                if (StringUtils.isNotBlank(url)) {
                    ProductFile file = new ProductFile();
                    file.setProductId(productVO.getId());
                    file.setUrl(url);
                    file.setCreateBy(productVO.getUpdateBy());
                    file.setCreateTime(new Date());
                    file.setUpdateBy(productVO.getUpdateBy());
                    file.setUpdateTime(new Date());
                    productFileService.insert(file);
                }
            }
        }

    }

    @Override
    public void firstCheck(ProductVO productVO) throws Exception {

        //1、更新标的状态
        Product update = new Product();
        update.setId(productVO.getId());
        if (ProductCheckStatusEnum.PASS.getStatus() == productVO.getCheckStatus()) {
            update.setProductStatus(ProductStatusEnum.FINAL_AUDIT.getStatus());
        } else {
            update.setProductStatus(ProductStatusEnum.FIRST_AUDIT_REFUSE.getStatus());
        }
        update.setUpdateBy(productVO.getUpdateBy());
        update.setUpdateTime(new Date());
        productService.updateById(update);

        //2、保存审核信息
        ProductCheck check = new ProductCheck();
        check.setProductId(productVO.getId());
        check.setFirstCheckStatus(productVO.getCheckStatus());
        check.setFirstCheckRemark(productVO.getFirstCheckRemark());
        check.setCreateBy(productVO.getUpdateBy());
        check.setCreateTime(new Date());
        check.setType(ProductCheckTypeEnum.PRODUCT.getStatus());
        productCheckService.insert(check);

        //发送站内信
        try {
            ProductVO product = productService.selectVOById(productVO.getId());
            UserMessageLog message = new UserMessageLog();
            message.setUserId(product.getBorrowUserId());
            message.setTitle(UserMessageTemplateEnum.MESSAGE_1.getTitle());
            String content = UserMessageTemplateEnum.MESSAGE_1.getContent();
            content = String.format(content, product.getBorrowUserName(),
                    product.getTitle());
            message.setContent(content);
//            userMessageLogService.sendMessage(message);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public void finalCheck(ProductVO productVO) throws Exception {
        //1、调用银行发标接口
        Product product = productService.selectById(productVO.getId());
        if (product == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有查询到有效的标的信息！");
        }
        Date beginDate = new Date();
        Date endDate = DateUtils.addDays(beginDate, product.getTimeCount());

//        ETKProductMaintenanceModel etkProductMaintenanceModel = null;
        //担保方用户信息
        UserInfo guaranteeUserInfo = null;
        //2、更新标的状态
        Product update = new Product();
        if (ProductCheckStatusEnum.PASS.getStatus() == productVO.getCheckStatus()) {
            /*ETKProductMaintenanceModel model = new ETKProductMaintenanceModel();

            model.setMerSubjectNo(product.getProductNo());
            model.setSubjectName(product.getTitle());
            model.setSubjectAmt(product.getProductAmount());
            //标的利率最多四位小数(0-1之间的小数，最后一位不能为0，不能出现0.10这种
            String subjectRate = product.getProfit().divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
            if (subjectRate.indexOf(".") > 0) {
                //正则表达
                subjectRate = subjectRate.replaceAll("0+?$", "");//去掉后面无用的零
                subjectRate = subjectRate.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            model.setSubjectRate(subjectRate);
            model.setSubjectPurpose(ProductUseTypeEnum.getDesc(product.getUseType()));
            //借款人信息
            UserInfo userInfo = userInfoService.selectById(product.getBorrowUserId());
            if (userInfo == null) {
                throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有查询到有效的借款人信息！");
            }
            model.setPayeeAcctNo(userInfo.getAcno());
            if (userInfo.getCustType().equals(CustTypeEnum.PERSONAL_BORROWER.getStatus())) {
                model.setSubjectType(ETKProductTypeEnum.USER.getId());
            } else if (userInfo.getCustType().equals(CustTypeEnum.COMPANY_BORROWER.getStatus())) {
                model.setSubjectType(ETKProductTypeEnum.COMPANY.getId());
            }
            model.setIdentificationNo(userInfo.getIdNumber());
            //标的服务费最多四位小数(0-1之间的小数，最后一位不能为0，不能出现0.10这种)
            String serviceRate = product.getServiceFee().divide(new BigDecimal(100)).setScale(4, BigDecimal.ROUND_HALF_UP).toString();
            if (serviceRate.indexOf(".") > 0) {
                //正则表达
                serviceRate = serviceRate.replaceAll("0+?$", "");//去掉后面无用的零
                serviceRate = serviceRate.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            model.setServiceRate(serviceRate);
            model.setSubjectStartDate(DateUtils.formatDate(beginDate, DateUtils.DATE_PARSE));
            model.setSubjectEndDate(DateUtils.formatDate(endDate, DateUtils.DATE_PARSE));
            //添加担保方信息
            if (product.getGuaranteeId() == null) {
                throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请求参数GuaranteeId不能为空！");
            }
            guaranteeUserInfo = userInfoService.selectByGuaranteeId(product.getGuaranteeId());
            if (guaranteeUserInfo == null || guaranteeUserInfo.getCompanyName() == null || guaranteeUserInfo.getAcno() == null) {
                throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有查询到有效的担保方信息！");
            }
            model.setGuarantor(guaranteeUserInfo.getCompanyName());
            model.setGuarantorAcct(guaranteeUserInfo.getAcno());

            etkProductMaintenanceModel = etkProductManagementServcie.productMaintenance(model);
            //调用发标接口成功后才能继续向下执行
            if ((!etkProductMaintenanceModel.getRespCode().equals(ETKRespCodeEnum.C00000.getCode())) ||
                    (StringUtils.isBlank(etkProductMaintenanceModel.getSubjectNo()))) {
                update.setId(product.getId());
                update.setProductNo(UUIDUtil.getUUID());
                productService.updateById(update);

                throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "请求银行发标接口失败：" + etkProductMaintenanceModel.getRespDesc());
            }*/
        }


        update.setId(productVO.getId());
        if (ProductCheckStatusEnum.PASS.getStatus() == productVO.getCheckStatus()) {
            update.setProductStatus(ProductStatusEnum.BIDING.getStatus());
            update.setInvestBeginDate(beginDate);
            update.setInvestEndDate(endDate);
            //把银行返回的标的编号存入库中
//            update.setSubjectNo(etkProductMaintenanceModel.getSubjectNo());
//            update.setChannelFlow(etkProductMaintenanceModel.getMerOrderNo());
        } else {
            update.setProductStatus(ProductStatusEnum.FINAL_AUDIT_REFUSE.getStatus());
        }

        update.setUpdateBy(productVO.getUpdateBy());
        update.setUpdateTime(new Date());
        productService.updateById(update);

        //判断是否需要将标自动加入智投
        /*if (YesOrNoEnum.YES.getId().equals(product.getIsJoinSmart())) {
            try {
                smartBidProductRelaService.addProductBySmartBidPlanId(product.getSmartBidPlanId(), product.getId(), productVO.getUpdateBy());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        //3、更新审核信息

        ProductCheck productCheck =
                productCheckService.selectByProductIdAndType(productVO.getId(), ProductCheckTypeEnum.PRODUCT.getStatus());
        if (productCheck != null) {
            ProductCheck check = new ProductCheck();
            check.setId(productCheck.getId());
            check.setFinalCheckStatus(productVO.getCheckStatus());
            check.setFinalCheckRemark(productVO.getFinalCheckRemark());
            check.setUpdateBy(productVO.getUpdateBy());
            check.setUpdateTime(new Date());
            productCheckService.updateById(check);
        }

        //发送复审站内信
        try {
            ProductVO productNow =
                    productService.selectVOById(productVO.getId());
            UserMessageLog message = new UserMessageLog();
            message.setUserId(productNow.getBorrowUserId());
            message.setTitle(UserMessageTemplateEnum.MESSAGE_2.getTitle());
            String content = UserMessageTemplateEnum.MESSAGE_2.getContent();
            content = String.format(content, productNow.getBorrowUserName(),
                    productNow.getTitle());
            message.setContent(content);
//            userMessageLogService.sendMessage(message);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void flowProduct(Long bidOrderId) throws Exception {

    }

    @Override
    public void flowProductRecordProcess(Long productId) throws Exception {

    }

    @Override
    public String getProductTitle(Integer assetType) throws Exception {
        return null;
    }

    @Override
    public void addRemindPhone(String phone) throws Exception {

    }

    @Override
    public void editRemindPhone(String oldPhone, String newPhone) throws Exception {

    }

    @Override
    public Page pageRemindPhones(Page page) throws Exception {
        return null;
    }

    @Override
    public void delRemindPhone(String phone) throws Exception {

    }

    @Override
    public void fullRemind() throws Exception {

    }

    @Override
    public void fullLoan(Long productId) throws Exception {

    }


}
