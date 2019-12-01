package com.yx.api.server.controller.user;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yx.api.server.annotation.SysLogOpt;
import com.yx.api.server.controller.BaseController;
import com.yx.business.rpc.api.UserBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.annotation.Authorization;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.user.common.enums.UserSourceEnum;
import com.yx.user.model.UserInfo;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.rpc.api.UserInfoService;
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
        model.setSource(UserSourceEnum.PC.getStatus());
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
    public ResultModel login(UserInfo model) throws Exception {
        String accessToken = userBusinessService.login(model, null);
        Map<String, Object> data = new HashMap<>();
        data.put(AuthorizationConstant.ACCESS_TOKEN_HEADER, accessToken);
        return ResultUtil.ok(data);
    }


    @ApiOperation(value = "用户退出", notes = "用户退出")
    @PostMapping("/logout")
    @SysLogOpt(module = "用户退出", value = "用户退出", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel logout(HttpServletRequest request) {

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
        return ResultUtil.ok(userInfoVO);
    }


}
