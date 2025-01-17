package com.chenxi.slimdyc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装线程池信息
 *
 * @author chenxi
 * @date 2025/1/13 12:17
 */
@Data
@AllArgsConstructor
public class ThreadPoolStatus {
    private String poolName;
    private int corePoolSize;
    private int maximumPoolSize;
    private int activeCount;
    private String queueType;
    private int activity;  // 活跃度：activeCount/maximumPoolSize
}
