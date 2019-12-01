package com.yx.admin.server.controller.sys;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.yx.admin.server.annotation.SysLogOpt;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.model.LoginModel;
import com.yx.common.core.util.SecurityUtil;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.web.BaseController;
import com.yx.common.web.model.ResultModel;
import com.yx.common.web.util.ResultUtil;
import com.yx.sys.model.SysUser;
import com.yx.sys.model.vo.SysUserVO;
import com.yx.sys.rpc.api.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆控制器
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 **/
@Slf4j
@RestController
@Api(value = "登录接口", description = "登录接口")
public class LoginController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * z
     * 获取验证码
     *
     * @param captchaId 验证码ID
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping("/captcha/{captchaId}")
    public ResultModel queryCaptcha(@PathVariable(value = "captchaId", required = false) String captchaId) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(116, 37, 4, 5);
        captcha.createCode();
        if (StrUtil.isBlank(captchaId) || !CacheUtil.getCache().exists(Constants.CacheNamespaceEnum.CAPTCHA.value() + captchaId)) {
            captchaId = RandomUtil.randomUUID();
        }
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.CAPTCHA.value() + captchaId, captcha.getCode(), 120);
        captcha.write(outputStream);
        Map<String, String> map = new HashMap<String, String>(2);
        map.put("captchaId", captchaId);
        map.put("captcha", Base64.encode(outputStream.toByteArray()));
        return ResultUtil.ok(map);
    }

    /**
     * 登陆
     *
     * @param loginModel 登录对象
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @SysLogOpt(module = "登录接口", value = "用户登录", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel login(@Valid @RequestBody LoginModel loginModel) throws Exception{
        // 校验验证码
        String redisCaptchaValue = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CAPTCHA.value() + loginModel.getCaptchaId());
        if (StrUtil.isBlank(redisCaptchaValue)) {
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_CAPTCHA_ERROR.value(), "验证码为空");
        }
        if (!redisCaptchaValue.equalsIgnoreCase(loginModel.getCaptchaValue())) {
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_CAPTCHA_ERROR.value(), "验证码错误");
        }
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginModel.getAccount(), SecurityUtil.encryptPassword(loginModel.getPassword()));
        Subject subject = SecurityUtils.getSubject();

        //同一账号记录登陆失败的次数--start
        Integer failCount =0;
        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + loginModel.getAccount());
        if(o!=null){
            failCount = Integer.valueOf(String.valueOf(o));
        }
        if(failCount>5){
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL.value(), "密码连续错误超过5次，账号将被锁定10分钟！");
        }
        //------------------------end

        try {
            subject.login(usernamePasswordToken);
        } catch (LockedAccountException e) {
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_LOCKED.value(), Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_LOCKED.getMessage());
        } catch (DisabledAccountException e) {
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_DISABLED.value(), Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_DISABLED.getMessage());
        } catch (ExpiredCredentialsException e) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.value(), Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.getMessage());
        } catch (UnknownAccountException e) {
            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_UNKNOWN.value(), Constants.ResultCodeEnum.LOGIN_FAIL_ACCOUNT_UNKNOWN.getMessage());
        } catch (IncorrectCredentialsException e) {
            //登陆失败--记录失败的次数，连续失败5次，需要等待10分钟
            failCount+=1;
            CacheUtil.getCache().set(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + loginModel.getAccount(),failCount,10*60);

            throw new BusinessException(Constants.ResultCodeEnum.LOGIN_FAIL_INCORRECT_CREDENTIALS.value(),Constants.ResultCodeEnum.LOGIN_FAIL_INCORRECT_CREDENTIALS.getMessage());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        // 清空验证码缓存
        CacheUtil.getCache().del(Constants.CacheNamespaceEnum.CAPTCHA.value() + loginModel.getCaptchaId());

        //清空登陆失败次数缓存
        CacheUtil.getCache().del(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + loginModel.getAccount());


        // 验证通过，返回前端所需的用户信息
        SysUser currentUser = (SysUser) super.getCurrentUser();
        SysUserVO sysUserModel = new SysUserVO();
        sysUserModel.setId(currentUser.getId());
        sysUserModel.setAccount(currentUser.getAccount());
        sysUserModel.setUserName(currentUser.getUserName());
        sysUserModel.setAvatar(currentUser.getAvatar());

        //登陆成功：
        sessionManager(sysUserModel,subject);

        return ResultUtil.ok(sysUserModel);
    }

    /**
     * 登陆成功后对session进行管理
     * @param sysUserModel
     * @param subject
     */
    private void sessionManager(SysUserVO sysUserModel,Subject subject){
        //获取前一次登陆的sessionId
        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.USER_ID_SESSION.value() + sysUserModel.getId());
        if(o!=null){
            String oldSessionId=(String)o;
            if(StringUtils.isNotBlank(oldSessionId)){
                //将之前登录用户的标识置为：踢出下线
                CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SESSION_USER_ID.value()+oldSessionId,AuthorizationConstant.KICKOUT, AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());
                //将之前用户session关联的信息删除
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER.value()+oldSessionId);
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.USER_ID_SESSION.value() + sysUserModel.getId());
            }
        }

        String sessionId=(String)subject.getSession().getId();

        // 1、sessionId和用户id关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SESSION_USER_ID.value()+sessionId,sysUserModel.getId(), AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());
        //2、将sesionId和用户信息关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SESSION_USER.value()+sessionId,sysUserModel, AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());
        //3、将用户id和sessionId关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.USER_ID_SESSION.value()+sysUserModel.getId(),sessionId, AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());

    }

    /**
     * 登出
     *
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    @SysLogOpt(module = "登出接口", value = "用户登出", operationType = Constants.LogOptEnum.LOGIN)
    public ResultModel logout() {
        try {
            Long currentUserId = getCurrentUserId();
            String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.USER_ID_SESSION.value() + currentUserId);
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER.value() + sessionId);
        }catch (Exception e){}

        SecurityUtils.getSubject().logout();
        return ResultUtil.ok(true);
    }

    /**
     * 未登陆
     *
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @RequestMapping(value = "/unlogin", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public ResultModel unlogin() {
        return ResultUtil.fail(Constants.ResultCodeEnum.UNLOGIN);
    }

    /**
     * 未授权
     *
     * @return ResultModel
     * @author TangHuaLiang
     * @date 2018-07-01
     */
    @RequestMapping(value = "/unauthorized", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    public ResultModel unauthorized() {
        return ResultUtil.fail(Constants.ResultCodeEnum.UNAUTHORIZED);
    }
}
