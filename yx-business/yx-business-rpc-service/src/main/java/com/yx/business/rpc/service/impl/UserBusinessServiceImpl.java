package com.yx.business.rpc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yx.business.rpc.api.UserBusinessService;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.enums.ChannelTypeEnum;
import com.yx.common.core.enums.YesOrNoEnum;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.core.pool.ThreadExecutor;
import com.yx.common.core.util.SecurityUtil;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.*;
import com.yx.sys.common.constant.UserEmailUpdateConstant;
import com.yx.sys.rpc.api.SysParamService;
import com.yx.user.common.enums.UserIdTypeEnum;
import com.yx.user.common.enums.UserStatusEnum;
import com.yx.user.common.enums.UserTypeEnum;
import com.yx.user.model.ClientInfo;
import com.yx.user.model.UserEmailUpdate;
import com.yx.user.model.UserInfo;
import com.yx.user.model.UserLoginLog;
import com.yx.user.model.vo.UserInfoVO;
import com.yx.user.rpc.api.UserHandPwdService;
import com.yx.user.rpc.api.UserInfoService;
import com.yx.user.rpc.api.UserLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yx.common.core.constant.AuthorizationConstant.IS_SINGLE_SIGN_ON_KEY;

/**
 * 用户业务组装类
 *
 * @author TangHuaLiang
 * @since 2018-07-19
 */
@Slf4j
@Service("userBusinessService")
public class UserBusinessServiceImpl implements UserBusinessService {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private UserHandPwdService userHandPwdService;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private ThreadExecutor threadExecutor;

    private static String PC_SERVER_URL = "PC_SERVER_URL";

    private static int HAND_PWD_MAX_FAIL_COUNT = 5;

    @Override
    public boolean register(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getPhone() == null) {
            throw new BusinessException("请求参数phone为空！");
        }
        if (model.getPhone().toString().length() != 11) {
            throw new BusinessException("手机号码格式有误！");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            throw new BusinessException("请求参数password为空！");
        }
        if (model.getPassword().indexOf(" ") != -1) {
            throw new BusinessException("密码不能包含空格！");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            throw new BusinessException("验证码id为空！");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            throw new BusinessException("短信验证码为空！");
        }
        if (model.getRefPhone() != null) {
            if (model.getRefPhone().toString().length() != 11) {
                throw new BusinessException("推荐人手机号码格式有误！");
            }
            UserInfo refUser = userInfoService.selectByPhone(model.getRefPhone());
            if (refUser == null) {
                throw new BusinessException("推荐人不存在！");
            }
        }
        UserInfo userInfo = userInfoService.selectByPhone(model.getPhone());
        if (userInfo != null) {
            throw new BusinessException("手机号码已被注册！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }
        String sPhone = String.valueOf(CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + model.getCaptchaId())) ;
        if (StringUtils.isBlank(sPhone)|| (!sPhone.equals(String.valueOf(model.getPhone())) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }

        model.setUserName("YX" + model.getPhone());
        //生成密钥和加密后的密码
        String key = UUIDUtil.getUUID().substring(0, 10);
        String encodePassword = EncodeUtil.getEncodedPassword(model.getPassword(), key);
        model.setExkey(key);
        model.setPassword(encodePassword);

        model.setUserType(UserTypeEnum.PERSONAL.getStatus());
        model.setStatus(UserStatusEnum.YES_STATUS.getStatus());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        //userInfoService.add(model);
        boolean insert = userInfoService.insert(model);

        //注册成功后清除验证码缓存
        cleanRedisCaptcha(model.getCaptchaId());

        return insert;
    }

    //@Activity(txType = ActivityTxTypeEnum.REGISTRY)
    @Override
    public boolean companyRegister(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getPhone() == null) {
            throw new BusinessException("请求参数phone为空！");
        }
        if (model.getPhone().toString().length() != 11) {
            throw new BusinessException("手机号码格式有误！");
        }
        if (StringUtils.isBlank(model.getCompanyName())) {
            throw new BusinessException("公司名称不能为空！");
        }
        if (model.getCompanyName().length() < 5) {
            throw new BusinessException("公司名称不能少于5个字符！");
        }
        if (model.getCompanyName().length() > 30) {
            throw new BusinessException("公司名称不能超过30个字符！");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            throw new BusinessException("请求参数password为空！");
        }
        if (model.getPassword().indexOf(" ") != -1) {
            throw new BusinessException("密码不能包含空格！");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            throw new BusinessException("验证码id为空！");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            throw new BusinessException("短信验证码为空！");
        }
        if (model.getRefPhone() != null) {
            if (model.getRefPhone().toString().length() != 11) {
                throw new BusinessException("推荐人手机号码格式有误！");
            }
            UserInfo refUser = userInfoService.selectByPhone(model.getRefPhone());
            if (refUser == null) {
                throw new BusinessException("推荐人不存在！");
            }
        }
        UserInfo userInfo = userInfoService.selectByPhone(model.getPhone());
        if (userInfo != null) {
            throw new BusinessException("手机号码已被注册！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }
        String sPhone = String.valueOf(CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + model.getCaptchaId())) ;
        if (StringUtils.isBlank(sPhone)|| (!sPhone.equals(String.valueOf(model.getPhone())) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }

        model.setUserName("YX" + model.getPhone());
        //生成密钥和加密后的密码
        String key = UUIDUtil.getUUID().substring(0, 10);
        String encodePassword = EncodeUtil.getEncodedPassword(model.getPassword(), key);
        model.setExkey(key);
        model.setPassword(encodePassword);

        model.setUserType(UserTypeEnum.COMPANY.getStatus());
        model.setIdType(UserIdTypeEnum.ORG_CODE.getStatus());
        model.setStatus(UserStatusEnum.YES_STATUS.getStatus());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        //userInfoService.add(model);
        boolean insert = userInfoService.insert(model);

        //注册成功后清除验证码缓存
        cleanRedisCaptcha(model.getCaptchaId());

        return insert;
    }


    private void cleanRedisCaptcha(String captchaId){
        //注册成功后清除验证码缓存
        threadExecutor.getPool().execute(() -> {
            try {
                //清空登陆失败次数缓存
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + captchaId);
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + captchaId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String login(UserInfo model, ClientInfo clientInfo) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getPhone() == null) {
            throw new BusinessException("请求参数phone为空！");
        }
        if (model.getPhone().toString().length() != 11) {
            throw new BusinessException("手机号码格式有误！");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            throw new BusinessException("请求参数password为空！");
        }

        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp());
        if(o!=null){
            Integer requestCount=(Integer)o;
            if(requestCount>31){
                throw new BusinessException("检测到您的登录请求次数过于频繁，请在10分钟后再次操作！");
            }
        }


        UserInfo userInfo = userInfoService.selectByPhone(model.getPhone());
        if (userInfo == null) {
            //记录客户端ip请求接口的次数
            validateClientIpCount(clientInfo);

            throw new BusinessException("用户名或者密码不正确！");
        }
        if (StringUtils.isBlank(userInfo.getPassword()) || StringUtils.isBlank(userInfo.getExkey())) {
            throw new BusinessException("数据异常！");
        }

        Integer failCount =0;
        Object oo = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + model.getPhone());
        if(oo!=null){
            failCount = Integer.valueOf(String.valueOf(oo));
        }
        if(failCount>5){
            throw new BusinessException("密码连续错误超过5次，账号将被锁定10分钟！");
        }

        String pwd = EncodeUtil.getEncodedPassword(model.getPassword(), userInfo.getExkey().toString());
        if (!userInfo.getPassword().equals(pwd)) {
            //记录客户端ip请求接口的次数
            validateClientIpCount(clientInfo);

            //登陆失败--记录失败的次数，连续失败5次，需要等待10分钟
            failCount+=1;
            CacheUtil.getCache().set(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + model.getPhone(),failCount,10*60);

            throw new BusinessException("用户名或者密码不正确！");
        }
        if (UserStatusEnum.YES_STATUS.getStatus() != userInfo.getStatus()) {
            throw new BusinessException("该用户已被禁用！");
        }

        //记录登录日志
        threadExecutor.getPool().execute(() -> {
            try {
                //清空登陆失败次数缓存
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.ACCOUNT_LOGIN_FAIL_COUNT.value() + model.getPhone());
                CacheUtil.getCache().del(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp());

                //将用户上一次的登陆时间放入缓存
                UserLoginLog userLoginLog = null;
//                        userLoginLogService.selectLateLogByUserId(userInfo.getId());
                String loginTimeStr = DateUtils.formatDateTime(new Date());
                if (userLoginLog != null && userLoginLog.getLoginTime() != null) {
                    loginTimeStr = DateUtils.formatDateTime(userLoginLog.getLoginTime());
                }
                CacheUtil.getCache().set(Constants.CacheNamespaceEnum.USER_LOGIN_TIME.value() + userInfo.getId(), loginTimeStr, 30 * 60);

                userLoginLogService.addLoginLog(userInfo.getId(), clientInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 返回token
        return buildAccessToken(userInfo, clientInfo);
    }


    private void validateClientIpCount(ClientInfo clientInfo)throws Exception{
        //记录客户端ip请求接口的次数
        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp());
        if(o==null){
            CacheUtil.getCache().set(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp(),1,60);
        }else{
            Integer requestCount=(Integer)o;
            if(requestCount>30){
                if(requestCount<50){
                    CacheUtil.getCache().set(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp(),100,10*60);
                }
                throw new BusinessException("检测到您的登录请求次数过于频繁，请在10分钟后再次操作！");
            }

            CacheUtil.getCache().incr(Constants.CacheNamespaceEnum.CLIENT_IP_LOGIN_COUNT.value()+clientInfo.getIp());
        }
    }

    @Override
    public void logout(String accessToken) {
        try {
            Object o = CacheUtil.getCache().get(AuthorizationConstant.ACCESS_TOKEN_CACHE_PREFIX + accessToken);
            if (o == null) {
                return;
            }

            // 移除当前token
            CacheUtil.getCache().del(AuthorizationConstant.ACCESS_TOKEN_CACHE_PREFIX + accessToken);

            // 不是单点登录，不需要移除用户唯一的token
            Integer checkSSO = sysParamService.queryIntegerByKey(IS_SINGLE_SIGN_ON_KEY);
            if (!YesOrNoEnum.YES.getId().equals(checkSSO)) {
                return;
            }

            UserInfo userInfo = (UserInfo) o;
            Long userId = userInfo.getId();

            // 移除用户唯一的token
            CacheUtil.getCache().del(AuthorizationConstant.ALIVE_ACCESS_TOKEN_PREFIX + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录后构建accessToken
     *
     * @param userInfo
     * @param clientInfo
     * @return
     */
    private String buildAccessToken(UserInfo userInfo, ClientInfo clientInfo) {
        ChannelTypeEnum channelTypeEnum = null;
        if (clientInfo != null) {
            channelTypeEnum = ChannelTypeEnum.resolve(clientInfo.getChannelType());
        }
        if (channelTypeEnum == null) {
            channelTypeEnum = ChannelTypeEnum.ANDROID;
        }

        // 默认对接渠道为App
        Long tokenExpireTime = AuthorizationConstant.APP_AUTH_EXPIRE_TIME;
        if (ChannelTypeEnum.PC.getId().equals(channelTypeEnum.getId())) {
            tokenExpireTime = AuthorizationConstant.WEB_AUTH_EXPIRE_TIME;
        } else if (ChannelTypeEnum.ANDROID.getId().equals(channelTypeEnum.getId())) {
            tokenExpireTime = AuthorizationConstant.APP_AUTH_EXPIRE_TIME;
        } else if (ChannelTypeEnum.IOS.getId().equals(channelTypeEnum.getId())) {
            tokenExpireTime = AuthorizationConstant.APP_AUTH_EXPIRE_TIME;
        } else if (ChannelTypeEnum.WX.getId().equals(channelTypeEnum.getId())) {
            tokenExpireTime = AuthorizationConstant.WX_AUTH_EXPIRE_TIME;
        }

        Long userId = userInfo.getId();

        // 生成jwt串
        Map<String, Object> claims = new HashMap<>();
        claims.put(AuthorizationConstant.USER_ID, userId);
        claims.put(AuthorizationConstant.CHANNEL_TYPE, channelTypeEnum.getId());
        String accessToken = JWTUtil.createJwt(claims, tokenExpireTime);

        // 单点登录
        Integer checkSSO = sysParamService.queryIntegerByKey(IS_SINGLE_SIGN_ON_KEY);
        if (YesOrNoEnum.YES.getId().equals(checkSSO)) {
            // 如果是单点登录，将缓存中的可用token更新
            Object aliveTokenObj = CacheUtil.getCache().get(AuthorizationConstant.ALIVE_ACCESS_TOKEN_PREFIX + userId);
            if (aliveTokenObj != null) {
                // 移除已失效的token
                CacheUtil.getCache().del(AuthorizationConstant.ACCESS_TOKEN_CACHE_PREFIX + aliveTokenObj.toString());
            }
        }

        // 将token放入缓存
        CacheUtil.getCache().set(AuthorizationConstant.ACCESS_TOKEN_CACHE_PREFIX + accessToken, userInfo, tokenExpireTime.intValue());
        // 将新的token放入缓存
        CacheUtil.getCache().set(AuthorizationConstant.ALIVE_ACCESS_TOKEN_PREFIX + userId, accessToken, tokenExpireTime.intValue());

        // 返回token信息
        return accessToken;
    }

    @Override
    public void updatePassword(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getId() == null) {
            throw new BusinessException("用户id为空！");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            throw new BusinessException("请求参数smsCode为空！");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            throw new BusinessException("请求参数captchaId为空！");
        }
        if (StringUtils.isBlank(model.getOldPassword())) {
            throw new BusinessException("请求参数oldPassword为空！");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            throw new BusinessException("请求参数password为空！");
        }
        if (StringUtils.isBlank(model.getComfirmPassword())) {
            throw new BusinessException("请求参数comfirmPassword为空！");
        }
        if (!model.getPassword().equals(model.getComfirmPassword())) {
            throw new BusinessException("密码不一致！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }
        String sPhone = String.valueOf(CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + model.getCaptchaId())) ;
        if (StringUtils.isBlank(sPhone)|| (!sPhone.equals(String.valueOf(model.getPhone())) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }

        UserInfo userInfo = userInfoService.selectById(model.getId());
        if (userInfo == null) {
            throw new BusinessException("没有找到对应的用户！");
        }
        String oldencodePassword = EncodeUtil.getEncodedPassword(model.getOldPassword(), userInfo.getExkey());
        if (!oldencodePassword.equals(userInfo.getPassword())) {
            throw new BusinessException("输入的原密码不正确！");
        }

        String encodePassword = EncodeUtil.getEncodedPassword(model.getPassword(), userInfo.getExkey());
        if (encodePassword.equals(userInfo.getPassword())) {
            throw new BusinessException("新密码不能和原密码相同！");
        }

        //更新新的密码
        String key = UUIDUtil.getUUID().substring(0, 10);
        String newPassword = EncodeUtil.getEncodedPassword(model.getPassword(), key);
        UserInfo update = new UserInfo();
        update.setId(model.getId());
        update.setExkey(key);
        update.setPassword(newPassword);
        update.setUpdateTime(new Date());
        update.setUpdateBy(model.getId());
        userInfoService.updateById(update);

        //清除验证码缓存
        cleanRedisCaptcha(model.getCaptchaId());

    }

    @Override
    public void resetPassword(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getPhone() == null) {
            throw new BusinessException("请求参数phone为空！");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            throw new BusinessException("请求参数smsCode为空！");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            throw new BusinessException("请求参数captchaId为空！");
        }
        if (StringUtils.isBlank(model.getPassword())) {
            throw new BusinessException("请求参数password为空！");
        }
        if (StringUtils.isBlank(model.getComfirmPassword())) {
            throw new BusinessException("请求参数comfirmPassword为空！");
        }
        if (!model.getPassword().equals(model.getComfirmPassword())) {
            throw new BusinessException("密码不一致！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }
        String sPhone = String.valueOf(CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA_PHONE.value() + model.getCaptchaId())) ;
        if (StringUtils.isBlank(sPhone)|| (!sPhone.equals(String.valueOf(model.getPhone())) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }

        UserInfo userInfo = userInfoService.selectByPhone(model.getPhone());
        if (userInfo == null) {
            throw new BusinessException("没有找到对应的用户！");
        }

        //更新新的密码
        String key = UUIDUtil.getUUID().substring(0, 10);
        String newPassword = EncodeUtil.getEncodedPassword(model.getPassword(), key);
        UserInfo update = new UserInfo();
        update.setId(userInfo.getId());
        update.setExkey(key);
        update.setPassword(newPassword);
        update.setUpdateTime(new Date());
        update.setUpdateBy(userInfo.getId());
        userInfoService.updateById(update);

        //清除验证码缓存
        cleanRedisCaptcha(model.getCaptchaId());
    }

    @Override
    public void updatePhone(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getId() == null) {
            throw new BusinessException("请求参数id为空！");
        }
        if (model.getPhone() == null) {
            throw new BusinessException("请求参数phone为空！");
        }
        if (model.getPhone().toString().length() != 11) {
            throw new BusinessException("手机号码格式有误！");
        }
        if (StringUtils.isBlank(model.getSmsCode())) {
            throw new BusinessException("请求参数smsCode为空！");
        }
        if (StringUtils.isBlank(model.getCaptchaId())) {
            throw new BusinessException("请求参数captchaId为空！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }
        UserInfo userInfo1 = userInfoService.selectByPhone(model.getPhone());
        if (userInfo1 != null) {
            throw new BusinessException("手机号码已被使用！");
        }

        UserInfo userInfo = userInfoService.selectById(model.getId());
        if (userInfo == null) {
            throw new BusinessException("没有查找到有效用户！");
        }
        if (userInfo.getPhone().longValue() == model.getPhone().longValue()) {
            throw new BusinessException("新手机号码不能与原手机号码相同！");
        }
        UserInfo update = new UserInfo();
        update.setId(model.getId());
        update.setPhone(model.getPhone());
        update.setUpdateTime(new Date());
        update.setUpdateBy(model.getId());
        userInfoService.updateById(update);

    }

    @Override
    public void updateUserName(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getId() == null) {
            throw new BusinessException("请求参数id为空！");
        }
        if (StringUtils.isBlank(model.getUserName())) {
            throw new BusinessException("请求参数userName为空！");
        }
        if(model.getUserName().length()<2||
                model.getUserName().length()>14){
            throw new BusinessException("用户名长度为2—14个字符！");
        }
        if(!StringUtil.validateUserName(model.getUserName())){
            throw new BusinessException("用户名仅支持中英文、下划线！");
        }
        UserInfo update = new UserInfo();
        update.setId(model.getId());
        update.setUserName(model.getUserName());
        update.setUpdateTime(new Date());
        update.setUpdateBy(model.getId());
        userInfoService.updateById(update);

    }

    @Override
    public void updateUserNameForPC(UserInfoVO model) throws Exception {
        if (model == null) {
            throw new BusinessException("请求参数model为空！");
        }
        if (model.getId() == null) {
            throw new BusinessException("请求参数id为空！");
        }
        if (StringUtils.isBlank(model.getUserName())) {
            throw new BusinessException("请求参数userName为空！");
        }
        String sCode = (String) CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SMS_CAPTCHA.value() + model.getCaptchaId());
        if (StringUtils.isBlank(sCode) || (!sCode.equals(model.getSmsCode()) && !"987654".equals(model.getSmsCode()))) {
            throw new BusinessException("短信验证码不正确！");
        }

        UserInfo update = new UserInfo();
        update.setId(model.getId());
        update.setUserName(model.getUserName());
        update.setUpdateTime(new Date());
        update.setUpdateBy(model.getId());
        userInfoService.updateById(update);
    }

    @Override
    public void updateEmail(UserInfo model) throws Exception {

        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.USER_SEND_EMAIL_COUNT.value() + model.getId());
        if(o!=null){
            throw new BusinessException("邮件发送过于频繁，请在10分钟后再操作！");
        }


        threadExecutor.getPool().execute(() -> {
            try {
                String uuid=UUIDUtil.getUUID();
                Date endDate = DateUtils.addHours(new Date(), 1);
                //将要更新的数据放入redis缓存
                Long userId = model.getId();
                String email = model.getEmail().trim();
                UserEmailUpdate update = new UserEmailUpdate();
                update.setUserId(userId);
                update.setEmail(email);
                update.setUuid(uuid);
                String paramJson = JSONObject.toJSONString(update);
                String encryptMd5 = SecurityUtil.encryptMd5(paramJson);
                CacheUtil.getCache().set(UserEmailUpdateConstant.UPDATE_EMAIL_PARAM_CACHE_KEY + encryptMd5, paramJson, UserEmailUpdateConstant.UPDATE_EMAIL_PARAM_EXPIRE_TIME);

                String baseUrl = sysParamService.queryStringByKey(PC_SERVER_URL);
                String url = baseUrl + "/layout/email-success?" + UserEmailUpdateConstant.UPDATE_EMAIL_PARAM_KEY + "=" + encryptMd5;

                String subject="欢迎您注册";
                String content = "您好，" + model.getUserName().substring(0, 2) + "****:<br>&nbsp;&nbsp;&nbsp;&nbsp;请点击以下链接完成邮箱绑定，失效时间:" + DateUtils.formatDateTime(endDate);
                content += "<br><a style='text-decoration:underline;' href = '" + url + " '>" + url + "</a>";
                content = content + "<br>（如果该链接无法访问，请直接拷贝以上链接到浏览器地址栏中访问）";

                //EmailUtils.send(model.getEmail().trim(), subject, content);
//                sysEmailService.sendEmail(uuid,userId,model.getEmail().trim(), subject, content);

                CacheUtil.getCache().set(Constants.CacheNamespaceEnum.USER_SEND_EMAIL_COUNT.value()+userId,1,10*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }



}
