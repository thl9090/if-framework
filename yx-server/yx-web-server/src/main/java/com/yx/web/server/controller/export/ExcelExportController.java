package com.yx.web.server.controller.export;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.yx.common.core.Constants;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.pool.ThreadExecutor;
import com.yx.common.oss.configuration.OSSFactory;
import com.yx.common.utils.FileUtils;
import com.yx.common.web.annotation.Authorization;
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
import com.yx.web.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
@CrossOrigin
@Api(value = "EXCEL导出", description = "EXCEL导出")
public class ExcelExportController extends BaseController {

    @Autowired
    private ExportLogService exportLogService;
    @Autowired
    private ProductService service;
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
    private static String EXPORT_MIN_TIME="EXPORT_MIN_TIME";



    @ApiOperation(value = "excel导出")
    @PostMapping("/export")
    @Authorization
    public ResultModel export(@RequestBody Map<String, Object> condition, HttpServletRequest request) throws Exception {
        //final Map<String, Object> condition;
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR,
                    "没有获取到当前用户");
        }
        if (condition == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到导出参数！");
        }
        Integer exportType = (Integer) condition.get("type");
        if (exportType == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有获取到导出类型！");
        }
        ExportTypeEnum resolve = ExportTypeEnum.resolve(exportType);
        if (resolve == null) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "没有匹配到导出类型！");
        }

        //判断是否导出过于频繁
        String minTime = sysParamService.queryStringByKey(EXPORT_MIN_TIME);
        if(StringUtils.isNotBlank(minTime)){
            Integer exportMinTime=Integer.valueOf(minTime);
            ExportLog exportLog = exportLogService.selectLateByUserIdAndType(currentUserId, exportType);
            if(exportLog!=null&&exportLog.getCreateTime()!=null){
                long between = DateUtil.between(new Date(), exportLog.getCreateTime(), DateUnit.MINUTE);
                if(between<exportMinTime){
                    return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "导出不要过于频繁，需要间隔"+minTime+"分钟！");
                }
            }
        }


        threadExecutor.getPool().execute(new Runnable() {
            @Override
            public void run() {
                long startTime = new Date().getTime();
                try {
                    //记录导出日志
                    Long exportLogId = createExportLog(resolve.getStatus(),
                            currentUserId);

                    String url = null;
                    switch (resolve) {
                        case EXPORT_RECOMMEND_LIST:
//                            url = exportRecommendList(condition, currentUserId);
                            break;
                        case EXPORT_WITHDRAW_LIST:
//                            url = exportWithdrawList(condition, currentUserId);
                            break;
                        case EXPORT_MYINVESTMENT_LIST://我的出借
//                            url = exportMyInvestmentList(condition, currentUserId);
                            break;
                        case EXPORT_MYINVESTMENTRECORD_LIST://出借记录
//                            url = exportMyInvestmentRecordList(condition, currentUserId);
                            break;
                        case EXPORT_MYBORROW_LIST://我的借款
//                            url = exportMyBorrowList(condition, currentUserId);
                            break;
                        case EXPORT_MYBORROWRECORD_LIST://借款申请
//                            url = exportMyBorrowRecordList(condition, currentUserId);
                            break;
                        case EXPORT_MYTRANSFER_LIST://债权转让
//                            url = exportMyTransferList(condition, currentUserId);
                            break;
                        case EXPORT_MYTRANSFERRECORD_LIST://转让记录
//                            url = exportMyTransferRecordList(condition, currentUserId);
                            break;
                        case EXPORT_BILLTRADESRECORD_LIST://电子账单交易记录
//                            url = exportBillTradesRecordList(condition, currentUserId);
                            break;
                        case EXPORT_BILLRECEIPTSRECORD_LIST://电子账单回款记录
//                            url = exportBillReceipTsrecordList(condition, currentUserId);
                            break;
                        case EXPORT_RECOMMENDDETAIL_LIST://提成明细
//                            url = exportRecommendDetailList(condition, currentUserId);
                            break;
                        //后续的其它导出，请在这里添加

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

        List<ProductExp> list = service.selectExpList(productVO);

        ExportParams params = new ExportParams("标的信息表", "标的信息");
        Workbook workbook = ExcelExportUtil.exportExcel(params,
                ProductExp.class, list);
        String filePath =
                TMP_FILE_PATH + System.currentTimeMillis() + EXCEL_EXTENSION;
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



}
