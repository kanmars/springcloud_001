package cn.kanmars.sn.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听机制：
 *  1、根据当前kernel_obj中的数据（GLOBAL_TASK_COUNT,GLOBAL_TASK_INDEX），监听对应的队列
 *  2、监听到消息之后，触发task任务
 * Created by baolong on 2016/4/11.
 */
public interface TaskListenerService {
    /** 监听初始化 */
    public void init();
    /** 监听销毁 */
    public void destory();
    /** 监听添加一个任务，并更新kernel_obj*/
    public void addTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
    /** 监听删除一个任务，并更新kernel_obj*/
    public void deleteTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
    /** 监听更新一个任务，并更新kernel_obj*/
    public void updateTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
}
