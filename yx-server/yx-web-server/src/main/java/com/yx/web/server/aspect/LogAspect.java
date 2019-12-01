package com.yx.web.server.aspect;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.yx.common.core.Constants;
import com.yx.common.core.constant.AuthorizationConstant;
import com.yx.common.core.util.RegexUtil;
import com.yx.common.utils.StringUtils;
import com.yx.common.web.model.ResultModel;
import com.yx.sys.model.SysLog;
import com.yx.sys.rpc.api.SysLogService;
import com.yx.user.common.enums.UserSourceEnum;
import com.yx.user.model.UserInfo;
import com.yx.user.rpc.api.UserInfoService;
import com.yx.web.server.annotation.SysLogOpt;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 日志入库切面
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
    /**
     * 开始时间
     */
    private long startTime = 0L;

    @Autowired
    private SysLogService logService;
    @Autowired
    private UserInfoService userInfoService;

    HttpServletRequest request=null;

    @Pointcut("execution(* *..controller..*.*(..)) && @annotation(com.yx.web.server.annotation.SysLogOpt)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        startTime = System.currentTimeMillis();
        Object result = null;
        SysLog sysLogModel = logPre(pjp);
        try {
            result = pjp.proceed();
        } finally {
            //查询类型不添加日志
            if (Constants.LogOptEnum.QUERY.value() != sysLogModel.getOperationType()) {
                if (logAfter(result, sysLogModel).getUserName() != null) {
                    //不入缓存库
                    sysLogModel.setCreateTime(new Date());
                    sysLogModel.setUpdateTime(new Date());
                    sysLogModel.setSource(UserSourceEnum.PC.getStatus());
                    logService.insert(sysLogModel);
                }
            }
        }
        return result;
    }


    private SysLog logPre(ProceedingJoinPoint pjp) throws Exception {
        SysLog sysLogModel = new SysLog();
        for (Method method : Class.forName(pjp.getTarget().getClass().getName()).getMethods()) {
            if (method.getName().equals(pjp.getSignature().getName())) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == pjp.getArgs().length) {
                    //方法名称
                    sysLogModel.setOperation(method.getAnnotation(SysLogOpt.class).value());
                    //操作类型
                    sysLogModel.setOperationType(method.getAnnotation(SysLogOpt.class).operationType().value());
                    break;
                }
            }
        }
        //获取请求对象
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        startTime = System.currentTimeMillis();
        String ip = HttpUtil.getClientIP(request);
        //方法名含包名（com.yx.sys.SysLogController.queryListPage）
        String classMethod = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();
        //请求参数
        String args = null;
        try {
            JSON.toJSONString(pjp.getArgs()).replaceAll(RegexUtil.getJSonValueRegex("password"), "****").replaceAll(RegexUtil.getJSonValueRegex("oldPassword"), "****");
        }catch (Exception e){

        }
        if(StringUtils.isNotBlank(args)&&args.length()>2000){
            args=null;
        }
        sysLogModel.setIp(ip);
        sysLogModel.setMethod(classMethod);
        sysLogModel.setParams(args);
        sysLogModel.setCreateTime(new Date());
        sysLogModel.setCreateBy(0L);
        sysLogModel.setUpdateBy(0L);
        //SysUser currentUser = (SysUser) WebUtil.getCurrentUser();
        UserInfo currentUser=null;
        try{
            currentUser=getCurrentUser(request);
        }catch (Exception e){
            currentUser=null;
        }
        if(currentUser==null){
            currentUser=new UserInfo();
            currentUser.setId(0L);
            currentUser.setUserName("0");
        }

        if (currentUser != null) {
            sysLogModel.setUserId(currentUser.getId());
            sysLogModel.setUserName(currentUser.getUserName());
        }
        return sysLogModel;
    }


    private SysLog logAfter(Object result, SysLog sysLogModel) {
        ResultModel response = null;
        if (result != null) {
            response = (ResultModel) result;
        }
        if (sysLogModel.getUserName() == null) {
            //SysUser user = (SysUser) WebUtil.getCurrentUser();
            UserInfo user=null;
            try {
                user=getCurrentUser(request);
            } catch (Exception e) {
                user=null;
            }
            if(user==null){
                user=new UserInfo();
                user.setId(0L);
                user.setUserName("0");
            }

            if (user != null) {
                sysLogModel.setUserId(user.getId());
                sysLogModel.setUserName(user.getUserName());
            }
        }
        //返回结果
        if (response != null && response.code == Constants.ResultCodeEnum.SUCCESS.value()) {
            sysLogModel.setResult(1);
        } else {
            sysLogModel.setResult(0);
        }
        //执行时长(毫秒)
        Long spendTime = System.currentTimeMillis() - startTime;
        sysLogModel.setTime(spendTime);
        return sysLogModel;
    }


    /**
     * 获取当前用户
     *
     * @return Object
     * @author TangHuaLiang
     * @date 2018-10-09
     */
    protected UserInfo getCurrentUser(HttpServletRequest request) /*throws BusinessException */{
        if(request==null){
            return null;
        }
        String userIdStr = request.getAttribute(AuthorizationConstant.USER_ID).toString();
        if (StringUtils.isBlank(userIdStr)) {
            return null;
        }

        Long userId = Long.valueOf(userIdStr);
        if (userId == null) {
            return null;
            //throw new BusinessException(Constants.ResultCodeEnum.UNAUTHORIZED.value(), "未获取到用户id");
        }
//        	UserInfo userInfo = (UserInfo)CacheUtil.getCache().get(Constants.APP_CURRENT_USER+userId);
        UserInfo userInfo = null;
        if (userInfo == null) {
            userInfo = userInfoService.selectById(userId);
            //用户信息放入缓存
            //CacheUtil.getCache().set(Constants.APP_CURRENT_USER+userId, userInfo, 5*60);
        }
        if (userInfo == null) {
            //throw new BusinessException(Constants.ResultCodeEnum.UNAUTHORIZED.value(), "未获取到用户信息");
            return null;
        }
        return userInfo;
    }


}
