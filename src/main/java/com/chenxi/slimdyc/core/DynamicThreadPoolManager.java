package com.chenxi.slimdyc.core;

import com.chenxi.slimdyc.domain.ThreadPoolStatus;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * 获取动态线程池信息
 *
 * @author chenxi
 * @date 2025/1/13 12:19
 */
public class DynamicThreadPoolManager {
    private final ApplicationContext applicationContext;

    public DynamicThreadPoolManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Map<String, DynamicThreadPool> getAllPools() {
        return applicationContext.getBeansOfType(DynamicThreadPool.class);
    }

    public List<ThreadPoolStatus> getAllPoolsStatus() {
        Map<String, DynamicThreadPool> allThreadPools = getAllPools();
        List<ThreadPoolStatus> threadPoolStatusList = new ArrayList<>();
        allThreadPools.forEach((key, value) -> {
            BlockingQueue<Runnable> queue = value.getQueue();
            String queueType = queue.getClass().getSimpleName();
            int activity = value.getActiveCount() * 100 / value.getMaximumPoolSize();
            threadPoolStatusList.add(new ThreadPoolStatus(key, value.getCorePoolSize(), value.getMaximumPoolSize(), value.getActiveCount(), queueType, activity));
        });
        return threadPoolStatusList;
    }


    public boolean updateStatus(String poolName, int corePoolSize, int maximumPoolSize) {
        DynamicThreadPool dycThreadPool = (DynamicThreadPool) applicationContext.getBean(poolName);
        dycThreadPool.updateCorePoolSize(corePoolSize);
        dycThreadPool.updateMaximumPoolSize(maximumPoolSize);

        return true;
    }
}
