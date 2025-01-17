package com.chenxi.slimdyc.core;

import java.util.concurrent.*;

/**
 * 动态线程池
 *
 * @author chenxi
 * @date 2025/1/13 12:18
 */
public class DynamicThreadPool extends ThreadPoolExecutor {

    public DynamicThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    // 动态调整核心线程数
    public void updateCorePoolSize(int newCorePoolSize) {
        this.setCorePoolSize(newCorePoolSize);
    }

    // 动态调整最大线程数
    public void updateMaximumPoolSize(int newMaximumPoolSize) {
        this.setMaximumPoolSize(newMaximumPoolSize);
    }
}
