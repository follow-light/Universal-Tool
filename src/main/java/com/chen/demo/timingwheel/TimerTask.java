package com.utils;

/**
 * 任务
 */
public class TimerTask {

    /**
     * 延迟时间
     */
    private long delayMs;

    /**
     * 任务
     */
    private Runnable task;

    private Integer index;

    /**
     * 时间槽
     */
    protected TimerTaskList timerTaskList;

    /**
     * 下一个节点
     */
    protected TimerTask next;

    /**
     * 上一个节点
     */
    protected TimerTask pre;

    /**
     * 描述
     */
    public String desc;

    public TimerTask(long delayMs, Runnable task, Integer index) {
        this.delayMs = System.currentTimeMillis() + delayMs;
        this.task = task;
        this.timerTaskList = null;
        this.next = null;
        this.pre = null;
        this.index = index;
    }

    public Runnable getTask() {
        return task;
    }

    public long getDelayMs() {
        return delayMs;
    }

    public Integer getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return desc;
    }
}
