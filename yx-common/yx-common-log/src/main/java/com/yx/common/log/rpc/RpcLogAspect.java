package com.yx.common.log.rpc;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.rpc.RpcContext;
import com.yx.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * rpc提供者和消费者日志打印
 *
 * @author TangHuaLiang
 * @date 2018-07-01
 */
@Slf4j
@Aspect
@Component
public class RpcLogAspect {

    /**
     * 开始时间
     */
    private long startTime = 0L;
    /**
     * 结束时间
     */
    private long endTime = 0L;

    @Pointcut("execution(* *..rpc.service..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Exception {
        Object result = null;
        BusinessException exception = null;
        try {
            log.info("doBeforeInServiceLayer");
            long startTime = System.currentTimeMillis();
            result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            log.info("doAfterInServiceLayer");
            try {
                RpcContext context = RpcContext.getContext();
                String interfaceName = context.getUrl().getParameter(Constants.INTERFACE_KEY);
                String methodName = context.getMethodName();
                log.info(String.format("调用接口：【%s】，方法：【%s】，耗时：【%s】毫秒", interfaceName, methodName, endTime - startTime));
            }catch (Exception e){
                //e.printStackTrace();
            }
        } catch (Throwable e) {
            e.printStackTrace();
            if (e instanceof BusinessException) {
                exception = (BusinessException) e;
            } else {
                exception = new BusinessException("服务器异常");
            }
        } finally {
            if (exception != null) {
                throw exception;
            }
        }
        return result;
    }

}
