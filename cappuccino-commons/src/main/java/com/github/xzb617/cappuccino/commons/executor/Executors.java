package com.github.xzb617.cappuccino.commons.executor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 要求指定线程池名称的线程池工具类
 * @author xzb617
 */
public class Executors {

    /**
     * 获取一个指定名称的定时类线程池（只有一个线程）
     * @param poolName 线程池名称
     * @param daemon 是否为守护线程
     * @return ScheduledExecutorService
     */
    public static ScheduledExecutorService newSingleThreadScheduledExecutor(String poolName, boolean daemon) {
        return java.util.concurrent.Executors.newSingleThreadScheduledExecutor(new Executors.CappuccinoThreadFactory(poolName, daemon));
    }

    /**
     * 线程工厂
     */
    static class CappuccinoThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final boolean daemon;

        CappuccinoThreadFactory(String poolName, boolean daemon) {
            SecurityManager s = System.getSecurityManager();
            this.group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            this.namePrefix = poolName + "-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
            this.daemon = daemon;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(this.group, r,
                    this.namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (!t.isDaemon())
                // 此处都设置为守护线程
                t.setDaemon(this.daemon);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
