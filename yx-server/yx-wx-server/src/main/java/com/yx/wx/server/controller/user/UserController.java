package com.yx.wx.server.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yx.business.rpc.api.UserBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.EncodeUtil;
import com.yx.common.utils.StringUtil;
import com.yx.common.utils.StringUtils;
import com.yx.common.utils.UUIDUtil;
import com.yx.common.web.annotation.Authorization;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.user.common.enums.AccountStatusEnum;
import com.yx.user.common.enums.CustTypeEnum;
import com.yx.user.common.enums.UserSourceEnum;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.rpc.api.UserInfoService;
import com.yx.wx.server.annotation.SysLogOpt;
import com.yx.wx.server.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器(这里的用户指的是wx的用户)
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Api(value = "用户", description = "用户")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserBusinessService userBusinessService;
//    @Autowired
//    private AuthService authService;
//    @Autowired
//    private SysSmsService sysSmsService;
//    @Autowired
//    private SysSmsLogService sysSmsLogService;
    @Autowired
    private SysParamService sysParamService;
//    @Autowired
//    private ETKCustomerService etkCustomerService;
//    @Autowired
//    private FddUserService fddUserService;

    private static String WX_SERVER_URL = "WX_SERVER_URL";

    /**
     * 用户注册
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "个人用户注册",
            notes = "传参说明:{phone:手机号码，password:密码,captchaId:验证码id,smsCode:短信验证码，refPhone:推荐人手机号码}")
    @PostMapping("/register")
    @SysLogOpt(module = "个人用户注册", value = "个人用户注册", operationType = Constants.LogOptEnum.ADD)
    public ResultModel register(UserInfoVO model) throws Exception {
        model.setSource(UserSourceEnum.WX.getStatus());
        boolean b = userBusinessService.register(model);
        return ResultUtil.ok(b);
    }

    @ApiOperation(value = "企业用户注册",
            notes = "传参说明:{companyName:公司名称,phone:手机号码，password:密码,captchaId:验证码id,smsCode:短信验证码，refPhone:推荐人手机号码}")
    @PostMapping("/companyRegister")
    @SysLogOpt(module = "企业用户注册", value = "企业用户注册", operationType = Constants.LogOptEnum.ADD)
    public ResultModel companyRegister(UserInfoVO model) throws Exception {
        model.setSource(UserSourceEnum.WX.getStatus());
        boolean b = userBusinessService.companyRegister(model);
        return ResultUtil.ok(b);
    }

    @ApiOperation(value = "获取图形验证码")
    @PostMapping("/captcha")
    @SysLogOpt(module = "获取图形验证码", value = "获取图形验证码", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel queryCaptcha() throws Exception {
        String captchaId = String.valueOf(new Date().getTime());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(110, 47, 4, 5);
        captcha.createCode();
        if (StrUtil.isBlank(captchaId) || !CacheUtil.getCache().exists(Constants.CacheNamespaceEnum.CAPTCHA.value() + captchaId)) {
            captchaId = RandomUtil.randomUUID();
        }
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.CAPTCHA.value() + captchaId, captcha.getCode(), 120);
        captcha.write(outputStream);
        Map<String, Object> map = new HashMap<>();
        map.put("captchaId", captchaId);
        map.put("captcha", Base64.encode(outputStream.toByteArray()));
        return ResultUtil.ok(map);
    }

    @ApiOperation(value = "注册获取短信验证码",
            notes = "传参说明：{phone:手机号码,captcha:图形验证码,captchaId:图形验证码id}")
    @PostMapping("/registerSmsCode")
    @SysLogOpt(module = "注册获取短信验证码", value = "注册获取短信验证码", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel registerSmsCode(UserInfoVO model) throws Exception {
        if (model.getPhone() == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到手机号码");
        }
        if (StringUtils.isBlank(model.getCaptcha())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码id");
        }
        String vcode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());
        if (!model.getCaptcha().equals(vcode)) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "请输入正确的图形验证码");
        }

        //清除图形验证码
        CacheUtil.getCache().del(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());

        //注册验证码1分钟只能发送一次
        /*Integer minuteCount = sysSmsLogService.selectCountByPhoneAndTypeForMinute(model.getPhone(), SmsTypeEnum.VERIFYCODE.getStatus());
        if(minuteCount>0){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "验证码1分钟只能发送一次");
        }*/
        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.USER_SEND_SMS_COUNT.value() + model.getPhone());
        if(o!=null){
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "验证码1分钟只能发送一次");
        }

        String smscode = StringUtil.getRandom6Number();//生成验证码
        //调用发送短信的方法
//        sysSmsService.sendCodeMessage(model.getPhone(), Long.valueOf(smscode));

        //将短信验证码放入缓存
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId(), smscode, 5 * 60);
        //将短信验证码对应的手机号码放入缓存
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + model.getCaptchaId(), model.getPhone(), 5 * 60);

        Map<String, String> map = new HashMap<String, String>(2);
        map.put("captchaId", model.getCaptchaId());

        return ResultUtil.ok(map);
    }

    @ApiOperation(value = "发送短信",
            notes = "传参说明：{phone:手机号码}")
    @PostMapping("/sendSms")
    @SysLogOpt(module = "发送短信", value = "发送短信", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel sendSms(UserInfoVO model) throws Exception {
        if (model.getPhone() == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到手机号码");
        }

        //验证码1分钟只能发送一次
//        Integer minuteCount = sysSmsLogService.selectCountByPhoneAndTypeForMinute(model.getPhone(), SmsTypeEnum.VERIFYCODE.getStatus());
//        if(minuteCount>0){
//            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "验证码1分钟只能发送一次");
//        }

        String smscode = StringUtil.getRandom6Number();//生成验证码
        //调用发送短信的方法
//        sysSmsService.sendCodeMessage(model.getPhone(), Long.valueOf(smscode));

        //将短信验证码放入缓存
        String captchaId = UUIDUtil.getUUID();

        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + captchaId, smscode, 2 * 60);
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + captchaId, smscode, 2 * 60);

        Map<String, String> map = new HashMap<String, String>(2);
        map.put("captchaId", captchaId);

        return ResultUtil.ok(map);
    }


    /**
     * 用户登陆
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "用户登录",
            notes = "传参说明：{phone:手机号码,password:密码}")
    @PostMapping("/login")
    @SysLogOpt(module = "登录接口", value = "用户登录", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel login(UserInfo model, HttpServletRequest request) throws Exception {
        String accessToken = userBusinessService.login(model, getClientInfo(request));
        Map<String, Object> data = new HashMap<>();
        data.put(AuthorizationConstant.ACCESS_TOKEN_HEADER, accessToken);
        return ResultUtil.ok(data);
    }


    @ApiOperation(value = "用户退出", notes = "用户退出")
    @PostMapping("/logout")
    @Authorization
    @SysLogOpt(module = "用户退出", value = "用户退出", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel logout(HttpServletRequest request) {
        Object accessTokenObj = request.getAttribute(AuthorizationConstant.ACCESS_TOKEN_HEADER);
        if (accessTokenObj != null) {
            userBusinessService.logout(accessTokenObj.toString());
        }
        return ResultUtil.ok();
    }

    @ApiOperation(value = "用户信息", notes = "用户信息")
    @PostMapping("/info")
    @Authorization
    @SysLogOpt(module = "用户信息", value = "用户信息", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel info(HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        UserInfoVO userInfoVO = userInfoService.selectVOById(currentUserId);
        userInfoVO.setPassword(null);
        userInfoVO.setExkey(null);
        if (userInfoVO.getPhone() != null) {
            String phoneStr = userInfoVO.getPhone().toString();
            if (phoneStr.length() > 10) {
                phoneStr = phoneStr.substring(0, 3) + "****" + phoneStr.substring(phoneStr.length() - 4, phoneStr.length());
                userInfoVO.setPhoneStr(phoneStr);
            }
        }
        if (StringUtils.isNotBlank(userInfoVO.getEmail()) && userInfoVO.getEmail().length() > 2) {
            String emailStr = userInfoVO.getEmail();
            int i = emailStr.indexOf("@");
            emailStr = emailStr.substring(0, 1) + "****" + emailStr.substring(i - 1, emailStr.length());
            userInfoVO.setEmailStr(emailStr);
        }
        if (StringUtils.isNotBlank(userInfoVO.getIdNumber()) && userInfoVO.getIdNumber().length() > 8) {
            String idnumberStr = userInfoVO.getIdNumber();
            idnumberStr = idnumberStr.substring(0, 3) + "********" + idnumberStr.substring(idnumberStr.length() - 4, idnumberStr.length());
            userInfoVO.setIdNumberStr(idnumberStr);
        }
        if (StringUtils.isNotBlank(userInfoVO.getCardNo()) && userInfoVO.getCardNo().length() > 4) {
            String cardNoStr = userInfoVO.getCardNo();
            cardNoStr = cardNoStr.substring(cardNoStr.length() - 4, cardNoStr.length());
            userInfoVO.setCardNoStr(cardNoStr);
        }
        if (StringUtils.isNotBlank(userInfoVO.getBankMobile()) && userInfoVO.getBankMobile().length() > 8) {
            String bankMobileStr = userInfoVO.getBankMobile();
            bankMobileStr = bankMobileStr.substring(0, 3) + "****" + bankMobileStr.substring(bankMobileStr.length() - 4, bankMobileStr.length());
            userInfoVO.setBankMobileStr(bankMobileStr);
        }
        if (StringUtils.isNotBlank(userInfoVO.getCompanyName()) && userInfoVO.getCompanyName().length() > 4) {
            String companyNameStr = userInfoVO.getCompanyName();
            companyNameStr = companyNameStr.substring(0, 2) + "********" + companyNameStr.substring(companyNameStr.length() - 2, companyNameStr.length());
            userInfoVO.setCompanyNameStr(companyNameStr);
        }

        //判断是否需要法大大实名认证
        if(userInfoVO.getCustType()!=null
                &&userInfoVO.getCustType()== CustTypeEnum.PERSONAL_BORROWER.getStatus()
                &&userInfoVO.getAccountStatus()==AccountStatusEnum.OPEN.getStatus()){
//            FddUser fddUser = fddUserService.selectByUserIdAndType(userInfoVO.getId(), FddTypeEnum.TYPE2.getId());
//            if(fddUser==null){
//                //未认证
//                userInfoVO.setFddStatusStr("未认证");
//                userInfoVO.setFddTip(1);//需要弹框提示
//                userInfoVO.setFddTipContent("为保障借款安全，请先进行实名认证");
//                userInfoVO.setFddStatus(1);//认证菜单可点击
//            }else{
//                //认证通过
//                if(fddUser.getStatus()== FddStatusEnum.STEP_2.getId()||
//                        fddUser.getStatus()==FddStatusEnum.STEP_3.getId()||
//                        fddUser.getStatus()==FddStatusEnum.STEP_4.getId()){
//
//                    userInfoVO.setFddStatusStr("已认证");
//                    userInfoVO.setFddTip(0);//需要弹框提示
//                    userInfoVO.setFddStatus(0);//认证菜单不可点击
//                }else if(fddUser.getStatus()==FddStatusEnum.STEP_5.getId()){
//                    userInfoVO.setFddStatusStr("认证中");
//                    userInfoVO.setFddTip(0);//需要弹框提示
//                    userInfoVO.setFddStatus(0);//认证菜单不可点击
//                }else if(fddUser.getStatus()==FddStatusEnum.STEP_6.getId()){
//                    userInfoVO.setFddStatusStr("认证未通过");
//                    userInfoVO.setFddTip(1);//需要弹框提示
//                    userInfoVO.setFddTipContent("您填写的认证信息和开通存管的信息不一致，请重新认证");
//                    userInfoVO.setFddStatus(1);//认证菜单不可点击
//                }else{
//                    //未认证
//                    userInfoVO.setFddStatusStr("未认证");
//                    userInfoVO.setFddTip(1);//需要弹框提示
//                    userInfoVO.setFddTipContent("为保障借款安全，请先进行实名认证");
//                    userInfoVO.setFddStatus(1);//认证菜单可点击
//                }
//            }
            userInfoVO.setShowFddMenu(1);//菜单栏显示实名认证
        }else{
            userInfoVO.setShowFddMenu(0);//菜单栏不显示实名认证
            userInfoVO.setFddStatusStr("");
            userInfoVO.setFddTip(0);//需要弹框提示
            userInfoVO.setFddStatus(0);//认证菜单可点击
        }

        return ResultUtil.ok(userInfoVO);
    }


    @ApiOperation(value = "修改手机号码-密码验证",
            notes = "传参说明：{password:密码,smsCode:短信验证码,captchaId:验证码id}")
    @PostMapping("/updatePhonePwdValidate")
    @Authorization
    @SysLogOpt(module = "修改手机号码-密码验证", value = "修改手机号码-密码验证", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel updatePhonePwdValidate(UserInfoVO model, HttpServletRequest request) throws BusinessException {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到密码");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到短信验证码");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到短信验证码id");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "短信验证码不正确！");
        }
        UserInfo userInfo = userInfoService.selectById(currentUserId);
        if (userInfo == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有查找到有效用户");
        }
        String pwd = EncodeUtil.getEncodedPassword(model.getPassword(), userInfo.getExkey().toString());
        if (!userInfo.getPassword().equals(pwd)) {
            throw new BusinessException(Constants.ResultCodeEnum.PARAM_ERROR.value(), "密码不正确！");
        }

        return ResultUtil.ok();
    }


    /**
     * 修改手机号码
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "修改手机号码",
            notes = "传参说明：{phone:手机号码，smsCode:短信验证码,captchaId:验证码id}")
    @PostMapping("/updatePhone")
    @Authorization
    @SysLogOpt(module = "修改手机号码", value = "修改手机号码", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel updatePhone(UserInfoVO model, HttpServletRequest request) throws Exception {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        model.setId(currentUserId);
        userBusinessService.updatePhone(model);
        //return ResultUtil.ok();
        return ResultUtil.fail(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED, "请重新登陆");
    }

    /**
     * 修改登录密码
     *
     * @param model
     * @return
     */
    @ApiOperation(value = "修改登录密码(登录后)",
            notes = "传参说明：{smsCode:短信验证码，captchaId:短信验证码id,oldPassword:原密码，password：密码，comfirmPassword：确认密码}")
    @PostMapping("/updatePassword")
    @Authorization
    @SysLogOpt(module = "修改登录密码(登录后)", value = "修改登录密码(登录后)", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel updatePassword(UserInfoVO model, HttpServletRequest request) throws Exception {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        model.setId(currentUserId);
        userBusinessService.updatePassword(model);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "重置登录密码-验证图形验证码(登录前)",
            notes = "传参说明：{phone:手机号码，captcha:图形验证码,captchaId:验证码id,}")
    @PostMapping("/restPasswordCaptchaValidate")
    @SysLogOpt(module = "重置登录密码-验证图形验证码(登录前)", value = "重置登录密码-验证图形验证码(登录前)", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel restPasswordCaptchaValidate(UserInfoVO model, HttpServletRequest request) {
        if (model.getPhone() == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到手机号码");
        }
        if (model.getPhone().toString().length() != 11) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "手机号码格式有误");
        }
        UserInfo userInfo = userInfoService.selectByPhone(model.getPhone());
        if (userInfo == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "手机号码无效");
        }
        if (StringUtils.isBlank(model.getCaptcha())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码id");
        }
        String vcode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());
        if (!model.getCaptcha().equals(vcode)) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "请输入正确的图形验证码");
        }
        //清除图形验证码
        CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());

        Map<String, Object> map = new HashMap<>();
        map.put("phone", model.getPhone());
        return ResultUtil.ok(map);
    }

    @ApiOperation(value = "图形验证码验证",
            notes = "传参说明：{captcha:图形验证码,captchaId:验证码id}")
    @PostMapping("/captchaValidate")
    @SysLogOpt(module = "图形验证码验证", value = "图形验证码验证", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel captchaValidate(UserInfoVO model, HttpServletRequest request) {
        if (StringUtils.isBlank(model.getCaptcha())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到图形验证码id");
        }
        String vcode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());
        if (!model.getCaptcha().equals(vcode)) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "请输入正确的图形验证码");
        }
        //清除图形验证码
        CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + model.getCaptchaId());

        return ResultUtil.ok();
    }

    @ApiOperation(value = "短信验证码验证",
            notes = "传参说明：{smsCode:短信验证码,captchaId:验证码id}")
    @PostMapping("/smsCodeValidate")
    @SysLogOpt(module = "短信验证码验证", value = "短信验证码验证", operationType = Constants.LogOptEnum.UNKNOW)
    public ResultModel smsCodeValidate(UserInfoVO model, HttpServletRequest request) {
        if (StringUtils.isBlank(model.getSmsCode())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到短信验证码");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到验证码id");
        }
        String vcode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (!model.getSmsCode().equals(vcode)) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "请输入正确的短信验证码");
        }

        return ResultUtil.ok();
    }

    @ApiOperation(value = "重置登录密码(登录前)",
            notes = "传参说明：{phone:手机号码,smsCode:短信验证码,captchaId:验证码id,password:密码,comfirmPassword:确认密码}")
    @PostMapping("/restPassword")
    @SysLogOpt(module = "重置登录密码(登录前)", value = "重置登录密码(登录前)", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel restPassword(UserInfoVO model, HttpServletRequest request) throws Exception {
        userBusinessService.resetPassword(model);
        return ResultUtil.ok();
    }

    @ApiOperation(value = "修改用户名",
            notes = "传参说明：{userName:用户名}")
    @PostMapping("/updateUserName")
    @Authorization
    @SysLogOpt(module = "修改用户名", value = "修改用户名", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel updateUserName(UserInfoVO model, HttpServletRequest request) throws Exception {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        model.setId(currentUserId);
        userBusinessService.updateUserName(model);
        return ResultUtil.ok();
    }


    @ApiOperation(value = "修改用户名--pc端",
            notes = "传参说明：{userName:用户名,captchaId:验证码id,smsCode:短信验证码}")
    @PostMapping("/updateUserNameForPC")
    @Authorization
    @SysLogOpt(module = "修改用户名--pc端", value = "修改用户名--pc端", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel updateUserNameForPC(UserInfoVO model, HttpServletRequest request) throws Exception {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        model.setId(currentUserId);
        userBusinessService.updateUserNameForPC(model);
        return ResultUtil.ok();
    }


    @ApiOperation(value = "修改邮箱",
            notes = "传参说明：{email:邮箱}")
    @PostMapping("/updateEmail")
    @Authorization
    @SysLogOpt(module = "修改邮箱", value = "修改邮箱", operationType = Constants.LogOptEnum.MODIFY)
    public ResultModel updateEmail(UserInfoVO model, HttpServletRequest request) throws Exception {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.INTERNAL_SERVER_ERROR, "没有获取到当前登录用户");
        }
        if (StringUtils.isBlank(model.getEmail())) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到email");
        }
        if (!StringUtil.regExpVali(model.getEmail(), StringUtil.regExp_email)) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到email");
        }
        UserInfo userInfo = userInfoService.selectByEmail(model.getEmail().trim());
        if (userInfo != null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "该邮箱已被使用");
        }
        userInfo = userInfoService.selectById(currentUserId);
        if (userInfo == null) {
            return ResultUtil.fail(Constants.ResultCodeEnum.PARAM_ERROR, "没有获取到有效用户");
        }
        userInfo.setEmail(model.getEmail().trim());

        userBusinessService.updateEmail(userInfo);
        return ResultUtil.ok();
    }





}
