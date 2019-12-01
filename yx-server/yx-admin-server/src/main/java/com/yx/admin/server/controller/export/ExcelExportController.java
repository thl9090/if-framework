package com.yx.admin.server.controller.export;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.pool.ThreadExecutor;
import com.yx.common.oss.configuration.OSSFactory;
import com.yx.common.utils.DateUtils;
import com.yx.common.utils.FileUtils;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.common.ExportStatusEnum;
import com.yx.sys.common.ExportTypeEnum;
import com.yx.sys.model.ExportLog;
import com.yx.sys.model.export.ProductExp;
import com.yx.sys.model.vo.ProductVO;
import com.yx.sys.rpc.api.ExportLogService;
import com.yx.sys.rpc.api.ProductService;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.user.model.vo.UserInfoExp;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.model.vo.UserOpenAccountExp;
import com.yx.user.rpc.api.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yx.sys.common.ExportTypeEnum.EXPORT_OPEN_ACCOUNT_USER_FEE_LIST;

/**
 * <p>
 * EXCEL导出前端控制器
 * </p>
 *
 * @author TangHuaLiang
 * @since 2018-08-12
 */
@Slf4j
@RestController
@RequestMapping("/excel")
@Api(value = "EXCEL导出", description = "EXCEL导出")
public class ExcelExportController extends BaseController {

    @Autowired
    private ExportLogService exportLogService;
    @Autowired
    private ProductService service;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private ThreadExecutor threadExecutor;


    //文件临时存放路径
    private static String TMP_FILE_PATH = System.getProperty("java.io.tmpdir");

    //文件扩展名
    private static String EXCEL_EXTENSION = ".xls";


    /**
     * 导出EXCEL最小时间间隔
     */
    private static String EXPORT_MIN_TIME = "EXPORT_MIN_TIME";


    @ApiOperation(value = "excel导出")
    @PostMapping("/export")
    public ResultModel productList(@RequestBody Map<String, Object> condition, HttpServletResponse response) {
        if (condition == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到导出类型");
        }
        Long currentUserId = getCurrentUserId();
        Integer exportType = (Integer) condition.get("type");
        if (exportType == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到导出类型");
        }
        ExportTypeEnum resolve = ExportTypeEnum.resolve(exportType);
        if (resolve == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到匹配的导出类型");
        }

        //判断是否导出过于频繁
        String minTime = sysParamService.queryStringByKey(EXPORT_MIN_TIME);
        if (StringUtils.isNotBlank(minTime)) {
            Integer exportMinTime = Integer.valueOf(minTime);
            ExportLog exportLog = exportLogService.selectLateByUserIdAndType(getCurrentUserId(), exportType);
            if (exportLog != null && exportLog.getCreateTime() != null) {
                long between = DateUtil.between(new Date(), exportLog.getCreateTime(), DateUnit.MINUTE);
                if (between < exportMinTime) {
                    return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "导出不要过于频繁，需要间隔" + minTime + "分钟！");
                }
            }
        }


        threadExecutor.getPool().execute(new Runnable() {
            @Override
            public void run() {
                long startTime = new Date().getTime();
                try {
                    //记录导出日志
                    Long exportLogId = createExportLog(resolve.getStatus(), currentUserId);

                    String url = null;
                    switch (resolve) {
                        case EXPORT_USER_LIST:
                            url = exportUserList(condition);
                            break;
                        case EXPORT_PRODUCT:
                            url = exportProductList(condition);
                            break;
                        //后续的其它导出，请在这里添加
                        case EXPORT_USER_FLOW:
                            url = exportUserFlowtList(condition);
                            break;
                        case EXPORT_BID_ORDER:
//                            url = exportBidOrderList(condition);
                            break;
                        case EXPORT_GUARANTEE_USER_ORDER:
                            url = exportGuaranteeUserList(condition);
                            break;
                        case EXPORT_GUARANTEE_REPAY_ORDER:
                            url = exportGuaranteeRepayList(condition);
                            break;
                        case EXPORT_GUARANTEE_FINISH_ORDER:
                            url = exportGuaranteeFinishList(condition);
                            break;
                        case EXPORT_ORDER:
                            url = exportOrderList(condition);
                            break;
                        case EXPORT_RECHARGE_ORDER:
                            url = exportRechargeList(condition);
                            break;
                        case EXPORT_WITHDRAW_ORDER:
                            url = exportWithdrawList(condition);
                            break;
                        case EXPORT_BORROW_APPLY:
                            url = exportBorrowApplyList(condition);
                            break;
                        case EXPORT_USER_SUGGEST:
                            url = exportUserSuggest(condition);
                            break;
                        case EXPORT_OVERDUERECORD_LIST://逾期还款计划列表
                            url = exportOverduerecordlist(condition);
                            break;
                        case EXPORT_PENDINGPLAN_LIST://待还还款计划列表
                            url = exportPendingplanList(condition);
                            break;
                        case EXPORT_REIMBURSEMENT_LIST://已还还款计划列表
                            url = exportReimbursementList(condition);
                            break;
                        case EXPORT_PACKAGE_RECEIVE_LIST://卡券包发放记录
                            url = exportPackageReceiveRecordList(condition);
                            break;
                        case EXPORT_COUPON_RECEIVE_LIST://卡券发放记录
                            url = exportCouponReceiveRecordList(condition);
                            break;
                        case EXPORT_RED_PACKAGE_USE_LIST://红包使用记录
                            url = exportRedPackageUseRecordList(condition);
                            break;
                        case EXPORT_ADD_INTEREST_USE_LIST://加息券使用记录
                            url = exportAddInterestUseRecordList(condition);
                            break;
                        case EXPORT_CASH_BACK_USE_LIST://现金券使用记录
                            url = exportCashBackUseRecordList(condition);
                            break;
                        case EXPORT_OPEN_ACCOUNT_USER_FEE_LIST://开户绑卡费用
                            url = exportOpenAccountUserFeeList(condition);
                            break;
                        case RECHARGE_FEE_LIST://充值银行手续费列表
                            url = exportRechargeFeeList(condition);
                            break;
                        case WITHDRAW_FEE_LIST://提现银行手续费列表
                            url = exportWithdrawFeeList(condition);
                            break;
                        case AUTH_RECORD_LIST://授权记录列表
                            url = exportAuthRecordList(condition);
                            break;
                        case SEND_EMAIL_LOG_LIST://邮件发送记录列表
                            url = exportSendEmailLogList(condition);
                            break;
                        case RECEIPT_LIST://收款计划列表
                            url = exportReceiptList(condition);
                            break;
                        case SMART_BID_PROCESS_LIST://智投已加入列表
                            url = exportSmartBidProcessList(condition);
                            break;
                        case SMART_BID_RECEIPT_LIST://智投收益中列表
                            url = exportSmartBidReceiptList(condition);
                            break;
                        case SMART_BID_OVER_LIST://智投已结束列表
                            url = exportSmartBidOverList(condition);
                            break;

                    }
                    //更新导出日志
                    long times = new Date().getTime() - startTime;
                    if (StringUtils.isNotBlank(url)) {
                        updateExportLog(exportLogId, times, url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                }
            }

        });

        return ResultUtil.ok(true);
    }

    private Long createExportLog(Integer type, Long createBy) throws BusinessException {
        ExportLog log = new ExportLog();
        log.setType(type);
        log.setStatus(ExportStatusEnum.PROCESSING.getStatus());
        log.setCreateTime(new Date());
        log.setCreateBy(createBy);
        log.setUpdateTime(new Date());
        log.setUpdateBy(createBy);
        ExportLog add = exportLogService.add(log);
        return add.getId();
    }

    private void updateExportLog(Long id, Long times, String fileUrl) throws BusinessException {
        ExportLog update = new ExportLog();
        update.setId(id);
        update.setFileUrl(fileUrl);
        update.setStatus(ExportStatusEnum.SUCCESS.getStatus());
        update.setTimes(times);
        update.setUpdateTime(new Date());
        exportLogService.updateById(update);
    }

    public String exportProductList(Map<String, Object> condition) {
        ProductVO productVO = new ProductVO();
        if (condition != null) {
            String jsonStr = JSON.toJSONString(condition);
            productVO = JSON.parseObject(jsonStr, ProductVO.class);
        }
        if (productVO.getCreateTimeStart() != null) {
            productVO.setCreateTimeStart(DateUtils.getOfDayFirst(productVO.getCreateTimeStart()));
        }
        if (productVO.getCreateTimeEnd() != null) {
            productVO.setCreateTimeEnd(DateUtils.getOfDayLast(productVO.getCreateTimeEnd()));
        }
        List<ProductExp> list = service.selectExpList(productVO);

        ExportParams params = new ExportParams("标的信息表", "标的信息");
        Workbook workbook = ExcelExportUtil.exportExcel(params, ProductExp.class, list);
        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
        FileUtils.createFile(filePath);
        File file = new File(filePath);
        try {
            workbook.write(new FileOutputStream(file));
            String url = OSSFactory.build().upload(file);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String exportUserFlowtList(Map<String, Object> condition) {
//        UserFlowVO userFlowVO = new UserFlowVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            userFlowVO = JSON.parseObject(jsonStr, UserFlowVO.class);
//        }
//        if (userFlowVO.getCreateTimeStart() != null) {
//            userFlowVO.setCreateTimeStart(DateUtils.getOfDayFirst(userFlowVO.getCreateTimeStart()));
//        }
//        if (userFlowVO.getCreateTimeEnd() != null) {
//            userFlowVO.setCreateTimeEnd(DateUtils.getOfDayLast(userFlowVO.getCreateTimeEnd()));
//        }
//        List<UserFlowExp> list = userFlowService.selectExpList(userFlowVO);
//
//        ExportParams params = new ExportParams("个人流水的信息表", "个人流水的信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, UserFlowExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private String exportBidOrderList(Map<String, Object> condition) {
//        BidOrderVO bidOrderVO = new BidOrderVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            bidOrderVO = JSON.parseObject(jsonStr, BidOrderVO.class);
//        }
//        if (bidOrderVO.getCreateTimeStart() != null) {
//            bidOrderVO.setCreateTimeStart(DateUtils.getOfDayFirst(bidOrderVO.getCreateTimeStart()));
//        }
//        if (bidOrderVO.getCreateTimeEnd() != null) {
//            bidOrderVO.setCreateTimeEnd(DateUtils.getOfDayLast(bidOrderVO.getCreateTimeEnd()));
//        }
//        if (bidOrderVO.getFullBidDateStart() != null) {
//            bidOrderVO.setCreateTimeStart(DateUtils.getOfDayFirst(bidOrderVO.getFullBidDateStart()));
//        }
//        if (bidOrderVO.getFullBidDateEnd() != null) {
//            bidOrderVO.setFullBidDateEnd(DateUtils.getOfDayLast(bidOrderVO.getFullBidDateEnd()));
//        }
//        List<BidOrderExp> list = bidOrderService.selectExpList(bidOrderVO);
//
//        ExportParams params = new ExportParams("投标列表的信息表", "投标列表的信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, BidOrderExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }


    /**
     * 导出渠道用户数据
     *
     * @param condition
     * @return
     */
    public String exportGuaranteeUserList(Map<String, Object> condition) {
//        Long currentUserId = getCurrentUserId();
//        GuaranteeInforUser guaranteeInforUser = new GuaranteeInforUser();
//
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            guaranteeInforUser = JSON.parseObject(jsonStr, GuaranteeInforUser.class);
//        }
//        if (guaranteeInforUser.getCreateTimeStart() != null) {
//            guaranteeInforUser.setCreateTimeStart(DateUtils.getOfDayFirst(guaranteeInforUser.getCreateTimeStart()));
//        }
//        if (guaranteeInforUser.getCreateTimeEnd() != null) {
//            guaranteeInforUser.setCreateTimeEnd(DateUtils.getOfDayLast(guaranteeInforUser.getCreateTimeEnd()));
//        }
//        guaranteeInforUser.setSysUserId(currentUserId);
//        List<GuaranteeUserExp> list = guaranteeInfoService.selectExpList(guaranteeInforUser);
//        ExportParams params = new ExportParams("用户基础数据导出", "用户基础数据导出");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, GuaranteeUserExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 导出渠道待还数据
     *
     * @param condition
     * @return
     */
    public String exportGuaranteeRepayList(Map<String, Object> condition) {
//        Long currentUserId = getCurrentUserId();
//        GuaranteeInforUser guaranteeInforUser = new GuaranteeInforUser();
//
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            guaranteeInforUser = JSON.parseObject(jsonStr, GuaranteeInforUser.class);
//        }
//        if (guaranteeInforUser.getCreateTimeStart() != null) {
//            guaranteeInforUser.setCreateTimeStart(DateUtils.getOfDayFirst(guaranteeInforUser.getCreateTimeStart()));
//        }
//        if (guaranteeInforUser.getCreateTimeEnd() != null) {
//            guaranteeInforUser.setCreateTimeEnd(DateUtils.getOfDayLast(guaranteeInforUser.getCreateTimeEnd()));
//        }
//        guaranteeInforUser.setSysUserId(currentUserId);
//        List<GuaranteeContentReyExp> list = guaranteeInfoService.selectExpReyList(guaranteeInforUser);
//        ExportParams params = new ExportParams("待还数据导出", "待还数据导出");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, GuaranteeContentReyExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 导出渠道已还清数据
     *
     * @param condition
     * @return
     */
    public String exportGuaranteeFinishList(Map<String, Object> condition) {
//        Long currentUserId = getCurrentUserId();
//        GuaranteeInforUser guaranteeInforUser = new GuaranteeInforUser();
//
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            guaranteeInforUser = JSON.parseObject(jsonStr, GuaranteeInforUser.class);
//        }
//        if (guaranteeInforUser.getCreateTimeStart() != null) {
//            guaranteeInforUser.setCreateTimeStart(DateUtils.getOfDayFirst(guaranteeInforUser.getCreateTimeStart()));
//        }
//        if (guaranteeInforUser.getCreateTimeEnd() != null) {
//            guaranteeInforUser.setCreateTimeEnd(DateUtils.getOfDayLast(guaranteeInforUser.getCreateTimeEnd()));
//        }
//        guaranteeInforUser.setSysUserId(currentUserId);
//        List<GuaranteeContentFinishExp> list = guaranteeInfoService.selectExpFinishList(guaranteeInforUser);
//        ExportParams params = new ExportParams("已还清数据导出", "已还清数据导出");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, GuaranteeContentFinishExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportOrderList(Map<String, Object> condition) {
//        OrderVO order = new OrderVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            order = JSON.parseObject(jsonStr, OrderVO.class);
//        }
//        List<OrderExp> list = bidOrderService.selectExpList(order);
//        ExportParams params = new ExportParams("借款统计信息", "借款统计信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, OrderExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 充值导出
     *
     * @param condition
     * @return
     */
    private String exportRechargeList(Map<String, Object> condition) {
//        RechargeQueryModelVO order = new RechargeQueryModelVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            order = JSON.parseObject(jsonStr, RechargeQueryModelVO.class);
//            if (order.getSearchDateStart() != null) {
//                String dateStart = DateUtils.formatDate(order.getSearchDateStart());
//                dateStart = dateStart + " 00:00:00";
//                order.setSearchDateStart(DateUtils.parseDate(dateStart));
//            }
//            if (order.getSearchDateEnd() != null) {
//                String dateEnd = DateUtils.formatDate(order.getSearchDateEnd());
//                dateEnd = dateEnd + " 23:59:59";
//                order.setSearchDateEnd(DateUtils.parseDate(dateEnd));
//            }
//        }
//        List<RechargeExp> rechargeExps = new ArrayList<>();
////                rechargeOrderService.queryForExport(order);
//        ExportParams params = new ExportParams("充值统计信息", "充值统计信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, RechargeExp.class, rechargeExps);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportWithdrawList(Map<String, Object> condition) {
//        WithdrawQueryModelVO order = new WithdrawQueryModelVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            order = JSON.parseObject(jsonStr, WithdrawQueryModelVO.class);
//            if (order.getSearchDateStart() != null) {
//                String dateStart = DateUtils.formatDate(order.getSearchDateStart());
//                dateStart = dateStart + " 00:00:00";
//                order.setSearchDateStart(DateUtils.parseDate(dateStart));
//            }
//            if (order.getSearchDateEnd() != null) {
//                String dateEnd = DateUtils.formatDate(order.getSearchDateEnd());
//                dateEnd = dateEnd + " 23:59:59";
//                order.setSearchDateEnd(DateUtils.parseDate(dateEnd));
//            }
//        }
//        List<WithdrawExp> withdrawExps = new ArrayList<>();
////                withdrawOrderService.queryForExport(order);
//        ExportParams params = new ExportParams("提现统计信息", "提现统计信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, WithdrawExp.class, withdrawExps);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportBorrowApplyList(Map<String, Object> condition) {
//        BorrowApplyRecordVO borrowApplyRecordVO = new BorrowApplyRecordVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            borrowApplyRecordVO = JSON.parseObject(jsonStr, BorrowApplyRecordVO.class);
//        }
//        List<BorrowApplyRecordExp> list = new ArrayList<>();
////                borrowApplyRecordService.selectExpList(borrowApplyRecordVO);
//        ExportParams params = new ExportParams("借款申请信息", "借款申请信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, BorrowApplyRecordExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportUserList(Map<String, Object> condition) {
        UserInfoVO userInfo = new UserInfoVO();
        if (condition != null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfo = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        List<UserInfoExp> list = this.userInfoService.selectExpList(userInfo);
        ExportParams params = new ExportParams("用户列表信息", "用户列表信息");
        Workbook workbook = ExcelExportUtil.exportExcel(params, UserInfoExp.class, list);
        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
        FileUtils.createFile(filePath);
        File file = new File(filePath);
        try {
            workbook.write(new FileOutputStream(file));
            String url = OSSFactory.build().upload(file);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String exportUserSuggest(Map<String, Object> condition) {
//        UserSuggestion userSuggestion = new UserSuggestion();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            userSuggestion = JSON.parseObject(jsonStr, UserSuggestion.class);
//        }
//        List<UserSuggestionExp> list = this.userSuggestionService.selectExpList(userSuggestion);
//        ExportParams params = new ExportParams("用户反馈信息", "用户反馈信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, UserSuggestionExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 导出逾期还款计划列表
     *
     * @param condition
     * @return
     */
    private String exportOverduerecordlist(Map<String, Object> condition) {
//        OverdueRecordVO overdueRecordVO = new OverdueRecordVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            overdueRecordVO = JSON.parseObject(jsonStr, OverdueRecordVO.class);
//        }
//        List<OverdueRecordExp> list = this.overdueRecordService.selectExportOverduerecordExpList(overdueRecordVO);
//        ExportParams params = new ExportParams("逾期还款计划列表", "逾期还款计划信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, OverdueRecordExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 导出待还还款计划列表
     *
     * @param condition
     * @return
     */
    private String exportPendingplanList(Map<String, Object> condition) {
//        RepaymentPendingPlanModel repaymentPendingPlanModel = new RepaymentPendingPlanModel();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            repaymentPendingPlanModel = JSON.parseObject(jsonStr, RepaymentPendingPlanModel.class);
//        }
//        if (repaymentPendingPlanModel.getCreateTimeStart() != null) {
//            String dateStart = DateUtils.formatDate(repaymentPendingPlanModel.getCreateTimeStart());
//            dateStart = dateStart + " 00:00:00";
//            repaymentPendingPlanModel.setCreateTimeStart(DateUtils.parseDate(dateStart));
//        }
//        if (repaymentPendingPlanModel.getCreateTimeEnd() != null) {
//            String dateEnd = DateUtils.formatDate(repaymentPendingPlanModel.getCreateTimeEnd());
//            dateEnd = dateEnd + " 23:59:59";
//            repaymentPendingPlanModel.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
//        }
//
//        if (repaymentPendingPlanModel.getRepayTimeStart() != null) {
//            String dateStart1 = DateUtils.formatDate(repaymentPendingPlanModel.getRepayTimeStart());
//            dateStart1 = dateStart1 + " 00:00:00";
//            repaymentPendingPlanModel.setRepayTimeStart(DateUtils.parseDate(dateStart1));
//        }
//        if (repaymentPendingPlanModel.getRepayTimeEnd() != null) {
//            String dateEnd1 = DateUtils.formatDate(repaymentPendingPlanModel.getRepayTimeEnd());
//            dateEnd1 = dateEnd1 + " 23:59:59";
//            repaymentPendingPlanModel.setRepayTimeEnd(DateUtils.parseDate(dateEnd1));
//        }
//        List<RepaymentPendingPlanExp> list = new ArrayList<>();
////                this.repaymentService.selectExportPendingplanList(repaymentPendingPlanModel);
//        ExportParams params = new ExportParams("待还还款计划列表", "待还还款计划信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, RepaymentPendingPlanExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /**
     * 导出已还还款计划列表
     *
     * @param condition
     * @return
     */
    private String exportReimbursementList(Map<String, Object> condition) {
//        RepaymentPendingPlanModel repaymentPendingPlanModel = new RepaymentPendingPlanModel();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            repaymentPendingPlanModel = JSON.parseObject(jsonStr, RepaymentPendingPlanModel.class);
//        }
//        if (repaymentPendingPlanModel.getCreateTimeStart() != null) {
//            String dateStart = DateUtils.formatDate(repaymentPendingPlanModel.getCreateTimeStart());
//            dateStart = dateStart + " 00:00:00";
//            repaymentPendingPlanModel.setCreateTimeStart(DateUtils.parseDate(dateStart));
//        }
//        if (repaymentPendingPlanModel.getCreateTimeEnd() != null) {
//            String dateEnd = DateUtils.formatDate(repaymentPendingPlanModel.getCreateTimeEnd());
//            dateEnd = dateEnd + " 23:59:59";
//            repaymentPendingPlanModel.setCreateTimeEnd(DateUtils.parseDate(dateEnd));
//        }
//
//        if (repaymentPendingPlanModel.getRepayTimeStart() != null) {
//            String dateStart1 = DateUtils.formatDate(repaymentPendingPlanModel.getRepayTimeStart());
//            dateStart1 = dateStart1 + " 00:00:00";
//            repaymentPendingPlanModel.setRepayTimeStart(DateUtils.parseDate(dateStart1));
//        }
//        if (repaymentPendingPlanModel.getRepayTimeEnd() != null) {
//            String dateEnd1 = DateUtils.formatDate(repaymentPendingPlanModel.getRepayTimeEnd());
//            dateEnd1 = dateEnd1 + " 23:59:59";
//            repaymentPendingPlanModel.setRepayTimeEnd(DateUtils.parseDate(dateEnd1));
//        }
//        List<RepaymentPendingPlanExp> list = new ArrayList<>();
////                this.repaymentService.exportReimbursementList(repaymentPendingPlanModel);
//        ExportParams params = new ExportParams("已还还款计划列表", "已还还款计划信息");
//        Workbook workbook = ExcelExportUtil.exportExcel(params, RepaymentPendingPlanExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportPackageReceiveRecordList(Map<String, Object> condition) {
//        List<ExportPackageReceiveRecordModel> exportPackageReceiveRecordModels = activityPackageReceiveRecordService.exportPackageReceiveRecord(condition);
//        ExportParams params = new ExportParams(EXPORT_PACKAGE_RECEIVE_LIST.getExcelName(), EXPORT_PACKAGE_RECEIVE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, ExportPackageReceiveRecordModel.class, exportPackageReceiveRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportCouponReceiveRecordList(Map<String, Object> condition) {
//        List<ExportCouponReceiveRecordModel> exportCouponReceiveRecordModels = activityCouponReceiveRecordService.exportCouponReceiveRecord(condition);
//        ExportParams params = new ExportParams(EXPORT_COUPON_RECEIVE_LIST.getExcelName(), EXPORT_COUPON_RECEIVE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, ExportCouponReceiveRecordModel.class, exportCouponReceiveRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportRedPackageUseRecordList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = activityCouponUseRecordService.queryRedPackageForAdminByPage(page);
//        List<QueryRedPackageUseRecordModel> queryRedPackageUseRecordModels = page.getRecords();
//        ExportParams params = new ExportParams(EXPORT_RED_PACKAGE_USE_LIST.getExcelName(), EXPORT_RED_PACKAGE_USE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryRedPackageUseRecordModel.class, queryRedPackageUseRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportAddInterestUseRecordList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = activityCouponUseRecordService.queryAddInterestForAdminByPage(page);
//        List<QueryAddInterestUseRecordModel> queryAddInterestUseRecordModels = page.getRecords();
//        ExportParams params = new ExportParams(EXPORT_ADD_INTEREST_USE_LIST.getExcelName(), EXPORT_ADD_INTEREST_USE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryAddInterestUseRecordModel.class, queryAddInterestUseRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportCashBackUseRecordList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = activityCouponUseRecordService.queryCashBackForAdminByPage(page);
//        List<QueryCashBackUseRecordModel> queryCashBackUseRecordModels = page.getRecords();
//        ExportParams params = new ExportParams(EXPORT_CASH_BACK_USE_LIST.getExcelName(), EXPORT_CASH_BACK_USE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryCashBackUseRecordModel.class, queryCashBackUseRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportOpenAccountUserFeeList(Map<String, Object> condition) {
        UserInfoVO userInfo = new UserInfoVO();
        if (condition != null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfo = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        List<UserOpenAccountExp> list = this.userInfoService.selectOpenAccountUserExp(userInfo);
        ExportParams params = new ExportParams(EXPORT_OPEN_ACCOUNT_USER_FEE_LIST.getExcelName(), EXPORT_OPEN_ACCOUNT_USER_FEE_LIST.getExcelName());
        Workbook workbook = ExcelExportUtil.exportExcel(params, UserOpenAccountExp.class, list);
        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
        FileUtils.createFile(filePath);
        File file = new File(filePath);
        try {
            workbook.write(new FileOutputStream(file));
            String url = OSSFactory.build().upload(file);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String exportRechargeFeeList(Map<String, Object> condition) {
//        List<QueryRechargeFeeModel> queryRechargeFeeModels = new ArrayList<>();
////                rechargeOrderService.exportFeeList(condition);
//        ExportParams params = new ExportParams(RECHARGE_FEE_LIST.getExcelName(), RECHARGE_FEE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryRechargeFeeModel.class, queryRechargeFeeModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportWithdrawFeeList(Map<String, Object> condition) {
//        List<QueryWithdrawFeeModel> queryWithdrawFeeModels = new ArrayList<>();
////                withdrawOrderService.exportFeeList(condition);
//        ExportParams params = new ExportParams(WITHDRAW_FEE_LIST.getExcelName(), WITHDRAW_FEE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryWithdrawFeeModel.class, queryWithdrawFeeModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportAuthRecordList(Map<String, Object> condition) {
//        List<QueryAuthRecordModel> queryAuthRecordModels = new ArrayList<>();
////                authService.exportAuthRecord(condition);
//        ExportParams params = new ExportParams(AUTH_RECORD_LIST.getExcelName(), AUTH_RECORD_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryAuthRecordModel.class, queryAuthRecordModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }


    private String exportSendEmailLogList(Map<String, Object> condition) {
//        SysEmailLogVO emailLogVO = new SysEmailLogVO();
//        if (condition != null) {
//            String jsonStr = JSON.toJSONString(condition);
//            emailLogVO = JSON.parseObject(jsonStr, SysEmailLogVO.class);
//        }
//        List<SysEmailLogExp> list = sysEmailService.selectExpList(emailLogVO);
//
//        ExportParams params = new ExportParams(SEND_EMAIL_LOG_LIST.getExcelName(), EXPORT_OPEN_ACCOUNT_USER_FEE_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, SysEmailLogExp.class, list);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportReceiptList(Map<String, Object> condition) {
//        List<QueryReceiptPlanForAdminModel> queryReceiptPlanForAdminModels = new ArrayList<>();
////                receiptService.exportReceiptPlanList(condition);
//        ExportParams params = new ExportParams(RECEIPT_LIST.getExcelName(), RECEIPT_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QueryReceiptPlanForAdminModel.class, queryReceiptPlanForAdminModels);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportSmartBidProcessList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = smartBidApplyService.queryForAdminByPage(page);
//        List<QuerySmartBidApplyForAdminModel> records = page.getRecords();
//        ExportParams params = new ExportParams(SMART_BID_PROCESS_LIST.getExcelName(), SMART_BID_PROCESS_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QuerySmartBidApplyForAdminModel.class, records);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportSmartBidReceiptList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = smartBidApplyService.queryForAdminByPage(page);
//        List<QuerySmartBidApplyForAdminModel> records = page.getRecords();
//        ExportParams params = new ExportParams(SMART_BID_RECEIPT_LIST.getExcelName(), SMART_BID_RECEIPT_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QuerySmartBidApplyForAdminModel.class, records);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    private String exportSmartBidOverList(Map<String, Object> condition) {
//        Page page = new Page(1, 10000);
//        page.setCondition(condition);
////        page = smartBidApplyService.queryForAdminByPage(page);
//        List<QuerySmartBidApplyForAdminModel> records = page.getRecords();
//        ExportParams params = new ExportParams(SMART_BID_OVER_LIST.getExcelName(), SMART_BID_OVER_LIST.getExcelName());
//        Workbook workbook = ExcelExportUtil.exportExcel(params, QuerySmartBidApplyForAdminModel.class, records);
//        String filePath = TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
//        FileUtils.createFile(filePath);
//        File file = new File(filePath);
//        try {
//            workbook.write(new FileOutputStream(file));
//            String url = OSSFactory.build().upload(file);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    /*private String exportBorrowerList(Map<String, Object> condition) {
        UserInfoVO userInfo = new UserInfoVO();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfo = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        List<UserInfoExp> list = this.userInfoService.selectBorrowerExpList(userInfo);
        ExportParams params = new ExportParams("借款人列表信息", "借款人列表信息");
        Workbook workbook = ExcelExportUtil.exportExcel(params, UserInfoExp.class, list);
        String filePath=TMP_FILE_PATH+System.currentTimeMillis()+EXCEL_EXTENSION;
        FileUtils.createFile(filePath);
        File file=new File(filePath);
        try {
            workbook.write(new FileOutputStream(file));
            String url= OSSFactory.build().upload(file);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    /*private String exportDepositaryList(Map<String, Object> condition) {
        UserInfoVO userInfo = new UserInfoVO();
        if(condition!=null) {
            String jsonStr = JSON.toJSONString(condition);
            userInfo = JSON.parseObject(jsonStr, UserInfoVO.class);
        }
        List<UserInfoExp> list = this.userInfoService.selectDepositaryExpList(userInfo);
        ExportParams params = new ExportParams("受托人列表信息", "受托人列表信息");
        Workbook workbook = ExcelExportUtil.exportExcel(params, UserInfoExp.class, list);
        String filePath=TMP_FILE_PATH+System.currentTimeMillis()+EXCEL_EXTENSION;
        FileUtils.createFile(filePath);
        File file=new File(filePath);
        try {
            workbook.write(new FileOutputStream(file));
            String url= OSSFactory.build().upload(file);
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/


}
