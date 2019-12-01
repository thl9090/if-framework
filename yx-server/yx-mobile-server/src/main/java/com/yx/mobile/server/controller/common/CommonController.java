//package com.yx.mobile.server.controller.common;
//
//import com.yx.business.model.base.BusinessBaseModel;
//import com.yx.common.core.Constants;
//import com.yx.common.core.enums.ChannelTypeEnum;
//import com.yx.common.core.exception.BusinessException;
//import com.yx.common.oss.configuration.OSSFactory;
//import com.yx.common.web.annotation.Authorization;
//import com.yx.user.model.ClientInfo;
//import com.yx.common.web.model.ResultModel;
//import com.yx.common.web.util.ResultUtil;
//import com.yx.mobile.server.annotation.SysLogOpt;
//import com.yx.mobile.server.controller.BaseController;
//import com.yx.sys.common.AppTypeEnum;
//import com.yx.sys.model.InfoDisplayModel;
//import com.yx.sys.model.appVersion.NewestAppVersionModel;
//import com.yx.sys.model.company.CompanyModel;
//import com.yx.sys.rpc.api.AppPackageVersionService;
//import com.yx.sys.rpc.api.SysParamService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * 公共属性controller
// *
// * @author YanBingHao
// * @since 2018/9/4
// */
//@Slf4j
//@Api(value = "公共", description = "公共属性")
//@RestController
//@RequestMapping(value = "/common")
//public class CommonController extends BaseController {
//
//    @Autowired
//    private SysParamService sysParamService;
//    @Autowired
//    private AppPackageVersionService appPackageVersionService;
//
//    @ApiOperation(value = "公司信息")
//    @PostMapping(value = "/companyInfo")
//    @SysLogOpt(module = "公司信息", value = "公司信息", operationType = Constants.LogOptEnum.QUERY)
//    public ResultModel companyInfo(CompanyModel model, HttpServletRequest request) throws Exception {
//        return ResultUtil.ok(sysParamService.selectCompanyInfo());
//    }
//
//    @ApiOperation(value = "信息展示")
//    @PostMapping(value = "/infoDisplay")
//    @SysLogOpt(module = "信息展示", value = "信息展示", operationType = Constants.LogOptEnum.QUERY)
//    public ResultModel infoDisplay(InfoDisplayModel model, HttpServletRequest request) throws Exception {
//        InfoDisplayModel infoDisplay = sysParamService.getInfoDisplay();
//        if(StringUtils.isNotBlank(infoDisplay.getInfoPublish())){
//            infoDisplay.setInfoPublish(infoDisplay.getInfoPublish()+"?styleType=app");
//        }
//        return ResultUtil.ok(infoDisplay);
//    }
//
//    @ApiOperation(value = "上传文件")
//    @PostMapping(value = "/uploadFile")
//    @Authorization
//    @SysLogOpt(module = "上传文件", value = "上传文件", operationType = Constants.LogOptEnum.ADD)
//    public ResultModel uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
//        if (file == null) {
//            return ResultUtil.fail("上传文件不能为空！");
//        }
//        String ossUrl = OSSFactory.build().upload(file);
//        log.info(String.format("上传的文件路径：【%s】", ossUrl));
//        return ResultUtil.ok(ossUrl);
//    }
//
//
//    @ApiOperation(value = "根据token查询用户信息", notes = "传参说明：{accessToken:token}")
//    @PostMapping(value = "/getUserInfoByToken")
//    @Authorization
//    @SysLogOpt(module = "根据token查询用户信息", value = "根据token查询用户信息", operationType = Constants.LogOptEnum.QUERY)
//    public ResultModel getUserInfoByToken(BusinessBaseModel model, HttpServletRequest request) throws Exception {
//        return ResultUtil.ok(getCurrentUser(request));
//    }
//
//    @ApiOperation(value = "查询最新的App版本")
//    @PostMapping(value = "/newestVersion")
//    public ResultModel newestVersion(NewestAppVersionModel model, HttpServletRequest request) throws Exception {
//        ClientInfo clientInfo = this.getClientInfo(request);
//        if (clientInfo == null) {
//            throw new BusinessException("客户端信息为空");
//        }
//
//        Integer channelType = clientInfo.getChannelType();
//        if (channelType != null) {
//            if (ChannelTypeEnum.ANDROID.getId().equals(channelType)) {
//                model.setAppType(AppTypeEnum.ANDROID.getId());
//            } else if(ChannelTypeEnum.IOS.getId().equals(channelType)){
//                model.setAppType(AppTypeEnum.IOS.getId());
//            }
//        }
//
//        String channelVersion = clientInfo.getChannelVersion();
//        if (StringUtils.isNotBlank(channelVersion)) {
//            model.setCurrentVersion(channelVersion);
//        }
//        return ResultUtil.ok(appPackageVersionService.getNewestVersion(model));
//    }
//
//}
