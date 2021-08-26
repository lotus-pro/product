package com.platform.core.threadPool;

import com.platform.common.context.SpringContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class ExecutorFactory {
    private static final String TASK_EXECUTOR = "taskExecutor";

    private ExecutorFactory() {
    }

    public static Executor get() {
        return (Executor) SpringContext.getBean("taskExecutor", Executor.class);
    }

    public static Executor get(String name) {
        return (Executor)SpringContext.getBean(name, Executor.class);
    }

    public static ThreadPoolTaskExecutor createThreadPoolTaskExecutor(int configuredPoolSize, String prefix) {
        int defaultPoolSize = Runtime.getRuntime().availableProcessors() * 4;
        int poolSize = Math.max(configuredPoolSize, defaultPoolSize);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize * 2);
        executor.setQueueCapacity(poolSize * 250);
        executor.setThreadNamePrefix(prefix);
        executor.initialize();
        return executor;
    }
}
