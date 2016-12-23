package cn.kanmars.sn.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册机制：
 *  1、保存当前系统内的任务信息
 *  2、不停的链接远程注册中心并将远程注册中心的数据取回并加工
 * Created by baolong on 2016/4/8.
 */
public interface TaskRegisterService {

    /** 注册机初始化 */
    public void init();
    /** 注册机销毁 */
    public void destory();
    /** 注册机添加一个任务，并更新kernel_obj*/
    public void addTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
    /** 注册机删除一个任务，并更新kernel_obj*/
    public void deleteTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
    /** 注册机更新一个任务，并更新kernel_obj*/
    public void updateTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj);
}
