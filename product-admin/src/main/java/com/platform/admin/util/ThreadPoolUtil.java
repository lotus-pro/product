package com.platform.admin.util;

import com.platform.admin.thread.TestThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @description: 自定义线程池工具类
 * @author: zengzheng
 * @create: 2020-11-14 13:43
 */
@Component
@Slf4j
public class ThreadPoolUtil {
    private final String TASK_NAME = "\n测试";
    public static ThreadPoolExecutor threadPool;

    private final static int MIN_POOL_SIZE = 5;
    private final static int MAX_POOL_SIZE = 8;
    private final static long FREE_TIME = 2;
    private final static int POOL_QUEUE_CATCH = 60;
    private ConcurrentHashMap<String, Object> syncInfoCache = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> errorTaskCache = new ConcurrentHashMap<>();
    private ArrayBlockingQueue<TestThread> prioQueue = new ArrayBlockingQueue<>(50);
    private static RejectedExecutionHandler threadFullHandler = (r,executor) -> {
        TestThread parser = (TestThread) r;

    };

    /**
     * dcs获取线程池
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(1, 2, FREE_TIME, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(1), threadFullHandler);
                }
                return threadPool;
            }
        }
    }

    /**
     * 获取处理异常错误信息
     * @return
     */
    public ConcurrentHashMap<String,Object> getErrorTashCache(){
        return this.errorTaskCache;
    }
    public ConcurrentHashMap<String,Object> getCache(){
        return this.syncInfoCache;
    }


    public void closePool(){
        threadPool.shutdown();
    }

    public void cleanCache(){
        prioQueue.clear();
        syncInfoCache.clear();
    }

    public void execute(TestThread testThread){
//        PriorityBlockingQueue<TestThread> prioQueue1 = (PriorityBlockingQueue) threadPool.getQueue();
//        TestThread peek = prioQueue1.peek();
//        if (Objects.nonNull(peek)) {
//            if (prioQueue1.contains(testThread)) {
//
//            }
//        }

//        if (prioQueue.contains(testThread)) {
//            System.out.println("已经存在的" + testThread.getNames());
//            return;
//        }
//        prioQueue.add(testThread);
        getThreadPool().execute(testThread);
    }

    public void aa() {
        System.out.println("activeCount：" + getThreadPool().getActiveCount());
        System.out.println("核心线程池大小：" + getThreadPool().getCorePoolSize());
        System.out.println("线程池大小：" + getThreadPool().getPoolSize());
        System.out.println("队里数量：" + getThreadPool().getQueue().size());
    }

}
