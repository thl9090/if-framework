package com.yx.mobile.server.controller.product;

import com.baomidou.mybatisplus.plugins.Page;
import com.yx.common.core.Constants;
import com.yx.common.core.model.PageModel;
import com.yx.common.utils.StringUtils;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.mobile.server.controller.BaseController;
import com.yx.sys.common.*;
import com.yx.sys.model.product.ProductFile;
import com.yx.sys.model.vo.ProductVO;
import com.yx.sys.rpc.api.ProductFileService;
import com.yx.sys.rpc.api.ProductService;
import com.yx.sys.rpc.api.SysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标的控制器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Api(value = "标的相关接口", description = "标的相关接口")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private ProductService productService;
//    @Autowired
//    private ReceiptTransferService receiptTransferService;
    @Autowired
    private ProductFileService productFileService;
//    @Autowired
//    private ReceiptService receiptService;

    /**
     * 图片水印后缀
     */
    private static String PIC_WATER_SUFFIX="PIC_WATER_SUFFIX";
    /**
     * 标的评级链接地址
     */
//    private static String PRODUCT_RATING_URL="PRODUCT_RATING_URL";
    private static String WX_SERVER_URL = "WX_SERVER_URL";

    private static String RESOURCE_SERVER_URL="RESOURCE_SERVER_URL";



    @ApiOperation(value = "首页标的--新手标", notes = "首页标的--新手标")
    @PostMapping("/indexNewUserProduct")
    public ResultModel indexNewUserProduct(ProductVO model) throws Exception {
        /*ProductVO vo = productService.selectIndexNewUserProduct();
        if(vo!=null){
            vo.setProductStatusDesc(ProductStatusEnum.resolve(vo.getProductStatus())==null?"":ProductStatusEnum.resolve(vo.getProductStatus()).getDesc());

            vo.setProfit(vo.getProfit().setScale(1));
            if (vo.getAddProfit() != null) {
                vo.setAddProfit(vo.getAddProfit().setScale(1));
            }
            vo.setProductAmount(vo.getProductAmount().setScale(2));
            vo.setMinBidAmount(vo.getMinBidAmount().setScale(2));

            vo.setProfitStr(vo.getProfit() != null ? vo.getProfit().toString() : "");
            vo.setAddProfitStr((vo.getAddProfit() != null && vo.getAddProfit().compareTo(BigDecimal.ZERO) > 0) ? vo.getAddProfit().toString() : "");
            vo.setProductAmountStr(vo.getProductAmount() != null ? vo.getProductAmount().toString() : "");
            vo.setMinBidAmountStr(vo.getMinBidAmount() != null ? vo.getMinBidAmount().toString() : "");
            if (vo.getProductAmount() != null && vo.getBidAmount() != null) {
                vo.setRestBidAmount(vo.getProductAmount().subtract(vo.getBidAmount()).setScale(2));
                vo.setRestBidAmountStr(vo.getRestBidAmount().toString());
            }
            //借款期限转化：如果还款方式是月，需要乘以30天
            if (ProductRepurchaseModeEnum.DENG_E_BEN_XI.getId() == vo.getRepurchaseMode() ||
                    ProductRepurchaseModeEnum.XIAN_XI_HOU_BEN.getId() == vo.getRepurchaseMode()) {
                vo.setProductDeadline(vo.getProductDeadline() * 30);
//            vo.setRepurchaseMode(ProductRepurchaseModeEnum.DAO_QI_HUAN_BEN_XI.getId());
            }
        }

        return ResultUtil.ok(vo);*/
        return ResultUtil.ok("");
    }

    /**
     * 首页标的列表
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "首页标的--优选项目", notes = "首页标的--优选项目")
    @PostMapping("/indexProduct")
    public ResultModel indexProduct(ProductVO model) throws Exception {
        List<ProductVO> list = productService.selectIndexProduct();
        for (ProductVO vo : list) {
            vo.setProductStatusDesc(ProductStatusEnum.resolve(vo.getProductStatus())==null?"":ProductStatusEnum.resolve(vo.getProductStatus()).getDesc());

            vo.setProfit(vo.getProfit().setScale(1));
            if (vo.getAddProfit() != null) {
                vo.setAddProfit(vo.getAddProfit().setScale(1));
            }
            vo.setProductAmount(vo.getProductAmount().setScale(2));
            vo.setMinBidAmount(vo.getMinBidAmount().setScale(2));

            vo.setProfitStr(vo.getProfit() != null ? vo.getProfit().toString() : "");
            vo.setAddProfitStr((vo.getAddProfit() != null && vo.getAddProfit().compareTo(BigDecimal.ZERO) > 0) ? vo.getAddProfit().toString() : "");
            vo.setProductAmountStr(vo.getProductAmount() != null ? vo.getProductAmount().toString() : "");
            vo.setMinBidAmountStr(vo.getMinBidAmount() != null ? vo.getMinBidAmount().toString() : "");
            if (vo.getProductAmount() != null && vo.getBidAmount() != null) {
                vo.setRestBidAmount(vo.getProductAmount().subtract(vo.getBidAmount()).setScale(2));
                vo.setRestBidAmountStr(vo.getRestBidAmount().toString());
            }
            //借款期限转化：如果还款方式是月，需要乘以30天
            if (ProductRepurchaseModeEnum.DENG_E_BEN_XI.getId().equals(vo.getRepurchaseMode()) ||
                    ProductRepurchaseModeEnum.XIAN_XI_HOU_BEN.getId().equals(vo.getRepurchaseMode())) {
                vo.setProductDeadline(vo.getProductDeadline() * 30);
                //vo.setRepurchaseMode(ProductRepurchaseModeEnum.DAO_QI_HUAN_BEN_XI.getId());
            }
        }
        return ResultUtil.ok(list);
    }


    @ApiOperation(value = "投资列表", notes = "传参说明：{currentPage：当前页码}")
    @PostMapping("/selectPage")
    public ResultModel selectPage(ProductVO model) {
        PageModel pageModel=new PageModel();
        if (model.getCurrentPage() != null) {
            pageModel.setCurrent(model.getCurrentPage());
        }

        ProductVO productVO = new ProductVO();
        List<Integer> productStatusList = new ArrayList<>();
        //招标中
        productStatusList.add(ProductStatusEnum.BIDING.getStatus());
        //还款中
        productStatusList.add(ProductStatusEnum.REPAYMENTING.getStatus());
        //已还清
        productStatusList.add(ProductStatusEnum.PAYOFF.getStatus());
        //逾期
        productStatusList.add(ProductStatusEnum.OVERDUE.getStatus());
        productVO.setProductStatusList(productStatusList);
        //借款标
        productVO.setProductType(ProductTypeEnum.LOAN.getStatus());
        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page<ProductVO> page = productService.selectBidPage(pageModel, productVO);
        for (ProductVO vo : page.getRecords()) {
            vo.setProductStatusDesc(ProductStatusEnum.resolve(vo.getProductStatus())==null?"":ProductStatusEnum.resolve(vo.getProductStatus()).getDesc());

            vo.setProfit(vo.getProfit().setScale(1));
            if (vo.getAddProfit() != null) {
                vo.setAddProfit(vo.getAddProfit().setScale(1));
            }
            vo.setProductAmount(vo.getProductAmount().setScale(2));
            vo.setMinBidAmount(vo.getMinBidAmount().setScale(2));

            vo.setAssetTypeMiniName(ProductAssetTypeEnum.resolve(vo.getAssetType()) == null ? "" : ProductAssetTypeEnum.resolve(vo.getAssetType()).getMiniName());
            vo.setProfitStr(vo.getProfit() != null ? vo.getProfit().toString() : "");
            vo.setAddProfitStr((vo.getAddProfit() != null && vo.getAddProfit().compareTo(BigDecimal.ZERO) > 0) ? vo.getAddProfit().toString() : "");
            vo.setProductAmountStr(vo.getProductAmount() != null ? vo.getProductAmount().toString() : "");
            vo.setMinBidAmountStr(vo.getMinBidAmount() != null ? vo.getMinBidAmount().toString() : "");
            if (vo.getProductAmount() != null && vo.getBidAmount() != null) {
                vo.setRestBidAmount(vo.getProductAmount().subtract(vo.getBidAmount()).setScale(2));
                vo.setRestBidAmountStr(vo.getRestBidAmount().toString());
            }
            //借款期限转化：如果还款方式是月，需要乘以30天
            if (ProductRepurchaseModeEnum.DENG_E_BEN_XI.getId().equals(vo.getRepurchaseMode()) ||
                    ProductRepurchaseModeEnum.XIAN_XI_HOU_BEN.getId().equals(vo.getRepurchaseMode())) {
                vo.setProductDeadline(vo.getProductDeadline() * 30);
//                vo.setRepurchaseMode(ProductRepurchaseModeEnum.DAO_QI_HUAN_BEN_XI.getId());
            }

            //已投金额所占百分比
            if(vo.getBidAmount()!=null&&vo.getBidAmount().compareTo(BigDecimal.ZERO)>0){
                String bidPercent = vo.getBidAmount().multiply(new BigDecimal(100)).divide(vo.getProductAmount(), 0, BigDecimal.ROUND_HALF_DOWN) + "%";
                vo.setBidPercent(bidPercent);
            }
        }

        return ResultUtil.ok(page);
    }


    @ApiOperation(value = "转让专区", notes = "传参说明：{currentPage：当前页码}")
    @PostMapping("/selectTransferPage")
    public ResultModel selectTransferPage(ProductVO model) {
        PageModel pageModel=new PageModel();
        if (model.getCurrentPage() != null) {
            pageModel.setCurrent(model.getCurrentPage());
        }

        ProductVO productVO = new ProductVO();
        List<Integer> productStatusList = new ArrayList<>();
        //招标中
        productStatusList.add(ProductStatusEnum.BIDING.getStatus());
        //还款中
        productStatusList.add(ProductStatusEnum.REPAYMENTING.getStatus());
        //已还清
        productStatusList.add(ProductStatusEnum.PAYOFF.getStatus());
        //逾期
        productStatusList.add(ProductStatusEnum.OVERDUE.getStatus());
        productVO.setProductStatusList(productStatusList);
        //债权标
        productVO.setProductType(ProductTypeEnum.DEBT.getStatus());
        productVO.setIsDel(ProductIsDelEnum.NO_DEL.getStatus());
        Page<ProductVO> page = productService.selectTransferPage(pageModel, productVO);
        for (ProductVO vo : page.getRecords()) {
            vo.setProductStatusDesc(ProductStatusEnum.resolve(vo.getProductStatus())==null?"":ProductStatusEnum.resolve(vo.getProductStatus()).getDesc());

            vo.setProfit(vo.getProfit().setScale(1));
            if (vo.getAddProfit() != null) {
                vo.setAddProfit(vo.getAddProfit().setScale(1));
            }
            vo.setProductAmount(vo.getProductAmount().setScale(2));
            vo.setMinBidAmount(vo.getMinBidAmount().setScale(2));

            vo.setAssetTypeMiniName("转");
            vo.setProfitStr(vo.getProfit() != null ? vo.getProfit().toString() : "");
            vo.setAddProfitStr((vo.getAddProfit() != null && vo.getAddProfit().compareTo(BigDecimal.ZERO) > 0) ? vo.getAddProfit().toString() : "");
            vo.setProductAmountStr(vo.getProductAmount() != null ? vo.getProductAmount().toString() : "");
            vo.setMinBidAmountStr(vo.getMinBidAmount() != null ? vo.getMinBidAmount().toString() : "");
            if (vo.getProductAmount() != null && vo.getBidAmount() != null) {
                vo.setRestBidAmount(vo.getProductAmount().subtract(vo.getBidAmount()).setScale(2));
                vo.setRestBidAmountStr(vo.getRestBidAmount().toString());
            }
            //借款期限转化：如果还款方式是月，需要乘以30天
//            if(ProductRepurchaseModeEnum.DENG_E_BEN_XI.getId()==vo.getRepurchaseMode()||
//                    ProductRepurchaseModeEnum.XIAN_XI_HOU_BEN.getId()==vo.getRepurchaseMode()){
//                vo.setProductDeadline(vo.getProductDeadline()*30);
//                vo.setRepurchaseMode(ProductRepurchaseModeEnum.DAO_QI_HUAN_BEN_XI.getId());
//            }
        }

        return ResultUtil.ok(page);
    }


    @ApiOperation(value = "标的信息",
            notes = "传参说明：{id：标的id}")
    @PostMapping("/info")
    public ResultModel info(ProductVO model) throws Exception {
        if (model.getId() == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到标的id");
        }
        ProductVO vo = productService.selectVOById(model.getId());
        vo.setProfit(vo.getProfit().setScale(1));
        if (vo.getAddProfit() != null) {
            vo.setAddProfit(vo.getAddProfit().setScale(1));
        }
        vo.setProductAmount(vo.getProductAmount().setScale(2));
        vo.setMinBidAmount(vo.getMinBidAmount().setScale(2));

        vo.setProfitStr(vo.getProfit() != null ? vo.getProfit().toString() : "");
        vo.setAddProfitStr((vo.getAddProfit() != null && vo.getAddProfit().compareTo(BigDecimal.ZERO) > 0) ? vo.getAddProfit().toString() : "");
        vo.setProductAmountStr(vo.getProductAmount() != null ? vo.getProductAmount().toString() : "");
        vo.setMinBidAmountStr(vo.getMinBidAmount() != null ? vo.getMinBidAmount().toString() : "");
        if (vo.getProductAmount() != null && vo.getBidAmount() != null) {
            vo.setRestBidAmount(vo.getProductAmount().subtract(vo.getBidAmount()).setScale(2));
            vo.setRestBidAmountStr(vo.getRestBidAmount().toString());
        }
        if (vo.getRepurchaseMode() != null) {
            vo.setRepurchaseModeDesc(ProductRepurchaseModeEnum.getMsg(vo.getRepurchaseMode()));
        }
        if (vo.getInvestEndDate() != null) {
            vo.setInvestEndDateTime(vo.getInvestEndDate().getTime());
        }
        //借款期限转化：如果还款方式是月，需要乘以30天
        if (ProductRepurchaseModeEnum.DENG_E_BEN_XI.getId().equals(vo.getRepurchaseMode()) ||
                ProductRepurchaseModeEnum.XIAN_XI_HOU_BEN.getId().equals(vo.getRepurchaseMode())) {
            if (ProductTypeEnum.DEBT.getStatus() != vo.getProductType()) {
                vo.setProductDeadline(vo.getProductDeadline() * 30);
            }
        }
        //债转标需要计算折价率
        //折价率=（债权转让金额-转让人持有利息）/债权转让本金
//        if (ProductTypeEnum.DEBT.getStatus() == vo.getProductType()) {
//            ReceiptTransfer receiptTransfer = receiptTransferService.selectByNewProductId(vo.getId());
//            if (receiptTransfer != null) {
////                BigDecimal divide = receiptTransfer.getTransferAmount().subtract(receiptTransfer.getTransferInterest()).divide(receiptTransfer.getTransferCapital());
////                vo.setRateOfDiscount(divide==null?"":divide.setScale(2).toString());
//                vo.setRateOfDiscount("100%");
//                vo.setTransferTotalAmountStr(receiptTransfer.getTotalAmount() == null ? "" : receiptTransfer.getTotalAmount().setScale(2).toString());
//                vo.setRecentRepaymentDateStr(receiptTransfer.getRecentRepaymentDate() == null ? "" : DateUtils.formatDate(receiptTransfer.getRecentRepaymentDate(), DateUtils.formatDate(receiptTransfer.getRecentRepaymentDate())));
//                vo.setExpectProfitStr(receiptTransfer.getTotalAmount().subtract(vo.getProductAmount()).setScale(2).toString());
//                vo.setCreateTimeStr(DateUtils.formatDate(vo.getCreateTime()));
//                vo.setOldProductId(receiptTransfer.getTopProductId());
//            }
//        }
        String borrowUserName = vo.getBorrowUserName();
        if(StringUtils.isNotBlank(borrowUserName)&&borrowUserName.length()>3){
            vo.setBorrowUserName(borrowUserName.charAt(0)+"***"+borrowUserName.substring(borrowUserName.length()-2,borrowUserName.length()) );
        }
        vo.setBorrowUserPhone(null);
        vo.setBorrowUserRealName(null);
        vo.setGuaranteeName(null);

        return ResultUtil.ok(vo);
    }

    @ApiOperation(value = "标的借款信息", notes = "传参说明：{id：标的id}")
    @PostMapping(value = "/borrowInfo")
    public ResultModel borrowInfo(ProductVO model, HttpServletRequest request) throws Exception {
        if (model.getId() == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到标的id");
        }
        ProductVO vo = productService.selectVOById(model.getId());
        if (vo == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到标的信息");
        }
        Map<String, Object> data = new HashMap<>();
        String borrowUserName=vo.getBorrowUserName();

        //标的评级
        ProductRatingEnum resolve = ProductRatingEnum.resolve(vo.getProductRating());
        //标的评级
        data.put("productRatingMiniName",resolve==null?"":resolve.getMiniName());
        String productRatingUrl = sysParamService.queryStringByKey(WX_SERVER_URL)+"/ping";
        //标的评级详情页url
        data.put("productRatingUrl",productRatingUrl);
        //借款人
        data.put("borrowerUserName",borrowUserName.charAt(0)+"***"+borrowUserName.substring(borrowUserName.length()-1,borrowUserName.length()) );
        //借款用途
        if(vo.getUseType().equals(ProductUseTypeEnum.OTHER.getStatus())){
            data.put("userType",vo.getUseTypeRemark());
        }else{
            data.put("userType", ProductUseTypeEnum.getDesc(vo.getUseType()) == null ? "" : ProductUseTypeEnum.getDesc(vo.getUseType()));
        }
        //借款详情
        data.put("borrowDesc", vo.getBorrowDesc());
        //风控描述
        data.put("riskDesc", vo.getRiskDesc());
        //借款材料
        List<ProductFile> productFiles = productFileService.selectByProductId(vo.getId());
        String picSuffix = sysParamService.queryStringByKey(PIC_WATER_SUFFIX);
        String resourceBaseUrl = sysParamService.queryStringByKey(RESOURCE_SERVER_URL);

        if(StringUtils.isNotBlank(picSuffix)){
            for(ProductFile file:productFiles){
                if(vo.getIsNewData()==0){
                    file.setUrl(resourceBaseUrl+"/images/"+file.getUrl());
                }else{
                    file.setUrl(file.getUrl()+picSuffix);
                }
            }
        }
        data.put("fileList", productFiles);

        return ResultUtil.ok(data);
    }



}
