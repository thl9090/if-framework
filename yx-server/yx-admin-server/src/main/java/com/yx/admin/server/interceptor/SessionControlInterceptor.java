package com.yx.admin.server.interceptor;

import cn.hutool.http.HttpUtil;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.exception.BusinessException;
import com.yx.common.redis.util.CacheUtil;
import com.yx.sys.model.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * session控制拦截器
 * admin的权限验证时基于session的，通过该拦截器实现：
 * 1、同一账号只能同时在一台设备登陆
 * 2、后台admin可以手动踢出在线用户
 */
@Slf4j
@Component
public class SessionControlInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Subject subject = SecurityUtils.getSubject();
        //如果没有登录,直接返回true
        if (!subject.isAuthenticated()) {
            return true;
        }

        String sessionId = (String)subject.getSession().getId();

        Object o = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);
        String userId=null;
        if(o!=null){
            userId=String.valueOf(o);
        }

        if(o==null||StringUtils.isBlank(userId)){
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);

            subject.logout();
            throw new BusinessException(Constants.ResultCodeEnum.UNLOGIN.value(), Constants.ResultCodeEnum.UNLOGIN.getMessage());
        }

        if(AuthorizationConstant.KICKOUT.equals(userId)){
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);

            subject.logout();
            throw new BusinessException(Constants.ResultCodeEnum.KICK_OUT.value(), "您的账号在其它地方登陆,请重新登陆！");
        }

        //执行到这一步说明当前用户没有被踢出，更新用户信息
        Object obj = CacheUtil.getCache().get(Constants.CacheNamespaceEnum.SESSION_USER.value() + sessionId);
        SysUserVO sysUserModel=null;
        if(obj!=null){
            sysUserModel=(SysUserVO)obj;
        }
        if(obj==null||sysUserModel==null){
            //没有获取到session对应的用户信息，所以强制退出登陆

            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER_ID.value() + sessionId);
            CacheUtil.getCache().del(Constants.CacheNamespaceEnum.SESSION_USER.value()+sessionId);

            subject.logout();
            throw new BusinessException(Constants.ResultCodeEnum.UNLOGIN.value(), Constants.ResultCodeEnum.UNLOGIN.getMessage());
        }
        String ip=getClientIp(request);
        if(StringUtils.isBlank(ip)){
            ip=HttpUtil.getClientIP(request);
        }

        sysUserModel.setIp(ip);
        sysUserModel.setUpdateTime(new Date());

        // 1、sessionId和用户id关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SESSION_USER_ID.value()+sessionId,sysUserModel.getId(), AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());
        //2、将sesionId和用户信息关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.SESSION_USER.value()+sessionId,sysUserModel, AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());
        //3、将用户id和sessionId关联
        CacheUtil.getCache().set(Constants.CacheNamespaceEnum.USER_ID_SESSION.value()+sysUserModel.getId(),sessionId, AuthorizationConstant.ADMIN_AUTH_EXPIRE_TIME.intValue());

        return true;
    }

    /**
     * 获取ip
     * @param request
     * @return
     */
    private String getClientIp(HttpServletRequest request){
        String ip="";
        try{
            ip=request.getHeader("X-Real-IP");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
        }catch (Exception e){
            ip="";
        }
        return ip;
    }


}
