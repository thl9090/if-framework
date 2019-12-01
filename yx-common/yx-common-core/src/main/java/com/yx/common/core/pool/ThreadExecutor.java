package com.yx.common.core.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author YanBingHao
 * @since 2018/12/28
 */
@Slf4j
@Component
public class ThreadExecutor {

    private static class ThreadExecutorHolder{
        public static final ThreadPoolTaskExecutor EXECUTOR = new ThreadPoolTaskExecutor();
        static {
            EXECUTOR.setCorePoolSize(30);
            EXECUTOR.setMaxPoolSize(100);
            EXECUTOR.setQueueCapacity(1000);
            EXECUTOR.setKeepAliveSeconds(300);
            EXECUTOR.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            EXECUTOR.initialize();
        }
    }

    public ThreadPoolTaskExecutor getPool(){
        ThreadPoolTaskExecutor executor = ThreadExecutorHolder.EXECUTOR;
        return executor;
    }

}
