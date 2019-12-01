package com.yx.common.web.interceptor;

import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.enums.ChannelTypeEnum;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.redis.util.CacheUtil;
import com.yx.common.utils.JWTUtil;
import com.yx.common.web.annotation.Authorization;
import com.yx.user.model.UserInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.yx.common.core.constant.AuthorizationConstant.*;


/**
 * 登录授权拦截器
 *
 * @author YanBingHao
 * @since 2018/11/26
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    public static final String REQUEST_PARAM_ACCESS_TOKEN = "accessToken";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 获取当前请求的渠道
        String requestChannelType = request.getHeader(CHANNEL_TYPE_HEADER);

        Authorization annotation = ((HandlerMethod) handler).getMethodAnnotation(Authorization.class);
        if (annotation == null) {
            return true;
        }

        // 获取密文的token
        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        if (StringUtils.isEmpty(accessToken)) {
            accessToken = request.getParameter(REQUEST_PARAM_ACCESS_TOKEN);
        }
        if (StringUtils.isEmpty(accessToken)) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EMPTY.value(), "accessToken信息为空");
        }

        log.info(String.format("Token拦截器获取到的token：【%s】", accessToken));

        if ("-1".equals(accessToken)) {
            return true;
        }

        // 校验密文的token有效性
        Claims claims = null;
        try {
            claims = JWTUtil.parseJwt(accessToken);
        } catch (Exception e) {
            throw new BusinessException(Constants.ResultCodeEnum.DECRYPTION_FAILED.value(), "accessToken信息解密失败");
        }

        // 查询缓存中是否有token信息
        Object o = CacheUtil.getCache().get(ACCESS_TOKEN_CACHE_PREFIX + accessToken);
        if (o == null) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.value(), "accessToken信息已失效");
        }
        UserInfo userInfo = (UserInfo) o;
        if (userInfo == null || userInfo.getId() == null) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.value(), "accessToken信息已失效");
        }

        // 获取token对接的渠道
        Object cto = claims.get(CHANNEL_TYPE);
        if (cto == null) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.value(), "accessToken信息不正确");
        }
        Integer tokenChannelType = Integer.valueOf(String.valueOf(cto));
        ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.resolve(tokenChannelType);
        if (channelTypeEnum == null) {
            throw new BusinessException(Constants.ResultCodeEnum.ACCESSTOKEN_EXPIRED.value(), "accessToken信息不正确");
        }
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

        // 延长token失效时间
        CacheUtil.getCache().set(ACCESS_TOKEN_CACHE_PREFIX + accessToken, userInfo, tokenExpireTime.intValue());
        CacheUtil.getCache().set(ALIVE_ACCESS_TOKEN_PREFIX + userInfo.getId(), accessToken, tokenExpireTime.intValue());

        request.setAttribute(AuthorizationConstant.USER_ID, userInfo.getId());
        request.setAttribute(AuthorizationConstant.ACCESS_TOKEN_HEADER, accessToken);
        request.setAttribute(AuthorizationConstant.CHANNEL_TYPE_HEADER, requestChannelType);
        return true;
    }

}
