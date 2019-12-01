package com.yx.web.server.controller.common;

import com.yx.common.core.constant.BankBackParamConstant;
import com.yx.common.oss.configuration.OSSFactory;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BackParam;
import com.yx.common.web.annotation.Authorization;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.web.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公共属性controller
 *
 * @author YanBingHao
 * @since 2018/9/4
 */
@Slf4j
@Api(value = "公共", description = "公共属性")
@RestController
@RequestMapping(value = "/common")
@CrossOrigin
public class CommonController extends BaseController {

    @Autowired
    private SysParamService sysParamService;

//    @ApiOperation(value = "公司信息")
//    @PostMapping(value = "/companyInfo")
//    public ResultModel companyInfo(CompanyModel model, HttpServletRequest request) throws Exception {
//        return ResultUtil.ok(sysParamService.selectCompanyInfo());
//    }
//
//    @ApiOperation(value = "信息展示")
//    @PostMapping(value = "/infoDisplay")
//    public ResultModel infoDisplay(InfoDisplayModel model, HttpServletRequest request) throws Exception {
//        return ResultUtil.ok(sysParamService.getInfoDisplay());
//    }

    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/uploadFile")
    @Authorization
    public ResultModel uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null) {
            return ResultUtil.fail("上传文件不能为空！");
        }
        String ossUrl = OSSFactory.build().upload(file);
        log.info(String.format("上传的文件路径：【%s】", ossUrl));
        return ResultUtil.ok(ossUrl);
    }

    @ApiOperation(value = "获取银行回跳参数信息")
    @PostMapping(value = "/getBackParam")
    public ResultModel getBackParam(BackParam param) throws Exception {
        String paramKey = param.getParamKey();
        log.info(String.format("根据key：【%s】查询回跳信息", paramKey));
        Object paramInfo = CacheUtil.getCache().get(BankBackParamConstant.BANK_BACK_PARAM_CACHE_KEY+paramKey);
        if (paramInfo == null) {
            log.error(String.format("根据key：【%s】查询回跳信息为空", paramKey));
            return ResultUtil.ok();
        }
        return ResultUtil.ok(paramInfo);
    }

}
