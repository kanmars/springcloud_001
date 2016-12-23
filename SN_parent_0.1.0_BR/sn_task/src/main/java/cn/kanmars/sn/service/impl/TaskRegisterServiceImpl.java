package cn.kanmars.sn.service.impl;


import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.com.sn.frame.util.DateUtils;
import cn.com.sn.frame.util.StringUtils;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.service.TaskListenerService;
import cn.kanmars.sn.service.TaskRegisterService;
import cn.kanmars.sn.utils.BasisConstants;
import cn.kanmars.sn.utils.QueryServerInfo;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/4/8.
 */
@Service
public class TaskRegisterServiceImpl implements TaskRegisterService {

    private HLogger logger = LoggerManager.getLoger("TaskRegisterServiceImpl");
    private ZkClient zkClient;
    private String zookeeperGroup = BasisConstants.ZOOKEEPER_DEFAULT_GROUP;

    @Autowired
    private TaskListenerService taskListenerService;

    /**
     * 注册机初始化
     */
    @PostConstruct
    public void init() {
        //初始化过程
        //1、读取配置文件
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)){
            String registerAddr = BasisConstants.registerAddr;
            String registerGroup = BasisConstants.registerGroup;
            zookeeperGroup = registerGroup;
            //2创建注册核心对象
            Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
            //3创建远程链接
            zkClient = new ZkClient(registerAddr);
            //4创建注册核心与远程链接同步任务
            for(Map.Entry<String,ConcurrentHashMap<String,Object>> e : kernel.entrySet()){
                String taskName = e.getKey();
                ConcurrentHashMap<String,Object> kernel_obj = e.getValue();
                addTask(taskName, kernel_obj);
            }

        }
    }

    /**
     * 注册机销毁
     */
    @PreDestroy
    public void destory() {
        //1、读取配置文件
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)){
            String registerAddr = BasisConstants.registerAddr;
            String registerGroup = BasisConstants.registerGroup;
            zookeeperGroup = registerGroup;
            //2创建注册核心对象
            Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
            //3创建远程链接
            zkClient = new ZkClient(registerAddr);
            //4创建注册核心与远程链接同步任务
            for(Map.Entry<String,ConcurrentHashMap<String,Object>> e : kernel.entrySet()){
                String taskName = e.getKey();
                ConcurrentHashMap<String,Object> kernel_obj = e.getValue();
                deleteTask(taskName, kernel_obj);
            }
            zkClient.close();
        }
    }


    public String createZKTaskListPath(String businessObjName,String group){
        return BasisConstants.ZOOKEEPER_BASE_PATH+"/"+businessObjName+"/"+group;
    }

    public String createZKTaskPathFileName(String businessObjName,String group,String taskname){
        return businessObjName+BasisConstants.SPLIT_HOSTNAME+ QueryServerInfo.getHostName()+BasisConstants.SPLIT_IP+QueryServerInfo.getIp()+BasisConstants.SPLIT_PID+ QueryServerInfo.getPid()+BasisConstants.SPLIT_TASKNAME+taskname;
    }
    public String createZKTaskPath(String businessObjName,String group,String taskname){
        return createZKTaskListPath(businessObjName, group)+"/"+createZKTaskPathFileName(businessObjName,group,taskname);
    }
    /**
     * 在kernel_obj中对taskPathFileName的数据进行刷新，刷新的根据是parentPath和currentChilds
     * @param kernel_obj            注册内核
     * @param taskPathFileName      目标文件名称  格式为createZKTaskPathFileName()方法的返回值
     * @param parentPath            监控的地址
     * @param currentChilds         其他路径列表
     */
    public void refreshKernelObj(ConcurrentHashMap<String,Object> kernel_obj,String taskPathFileName,String parentPath, List<String> currentChilds){
        //获取总数和当前数量，并且设置到kernel中
        int count = 0;
        int currentIdx = 0;
        List<String> currentChilds_sort = new LinkedList<String>();
        currentChilds_sort.addAll(currentChilds);
        Collections.sort(currentChilds_sort);
        for(int i=0;i<currentChilds_sort.size();i++){
            String childPath = currentChilds_sort.get(i);
            try{
                String taskName_tmp = childPath.substring(childPath.lastIndexOf(BasisConstants.SPLIT_TASKNAME)+9);//jobId_i
                String businessObjName = childPath.substring(0,childPath.indexOf("_"));//businessObjName
                if(taskPathFileName.equals(childPath)){
                    currentIdx = count;
                }
                count++;
            }catch (Exception e){
                logger.error("内核刷新异常",e);
            }
        }
        kernel_obj.put(BasisConstants.KERNELOBJ_GLOBAL_TASK_COUNT, count+"");
        kernel_obj.put(BasisConstants.KERNELOBJ_GLOBAL_TASK_INDEX, currentIdx + "");
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)){
            kernel_obj.put(BasisConstants.KERNELOBJ_GLOBAL_REGISTER_PATH,parentPath+"/"+taskPathFileName);//注册地址
            kernel_obj.put(BasisConstants.KERNELOBJ_zkClient, zkClient);
        }
    }

    /**
     * 监听一个路径并设置刷新事件
     * @param zkClient
     * @param subscribePath     被监听的地址
     * @param taskPath           任务的全路径
     * @param taskPathFileName  目标文件名称
     * @param kernel_obj    内核对象
     */
    public void subscribeChildChanges(ZkClient zkClient,String subscribePath,String taskPath,final String taskPathFileName,final ConcurrentHashMap<String,Object> kernel_obj,final String taskName){
        //建立该路径的监听事件
        IZkChildListener iZkChildListener = new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                //在kernel_obj中对taskPathFileName的数据进行刷新，刷新的根据是parentPath和currentChilds
                refreshKernelObj(kernel_obj, taskPathFileName, parentPath, currentChilds);
            }
        };
        kernel_obj.put(BasisConstants.KERNELOBJ_IZkChildListener,iZkChildListener);
        zkClient.subscribeChildChanges(subscribePath, iZkChildListener);
    }

    /**
     * 递归保障文件存在
     * @param zkClient
     * @param path
     */
    public void mkZKDirs(ZkClient zkClient,String path){
        String[] pathArray = path.split("/");
        String currentPath = "";
        for(String pathNode : pathArray){
            if(StringUtils.isEmpty(pathNode))continue ;
            currentPath +="/"+pathNode;
            try{
                zkClient.create(currentPath, "", CreateMode.PERSISTENT);
            }catch (Exception e){
                if(!zkClient.exists(currentPath)){
                    logger.error("创建zookeeperDir异常",e);
                    throw new RuntimeException("");
                }
            }
        }
    }

    /**
     * 注册机添加一个任务:
     *      1、建立监听
     *      2、创建临时路径
     *
     * @param taskName
     * @param kernel_obj
     */
    public void addTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj) {
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)){
            String businessObjName = (String)kernel_obj.get(BasisConstants.KERNELOBJ_businessObjName);
            String path = createZKTaskListPath(businessObjName, zookeeperGroup);
            //确保永久文件夹存在
            mkZKDirs(zkClient,path);
            //创建临时文件并建立监听
            String taskPathFileName = createZKTaskPathFileName(businessObjName, zookeeperGroup ,taskName);//文件名
            String taskPath = createZKTaskPath(businessObjName, zookeeperGroup ,taskName);//全路径
            //对kernelObj中设置GLOBAL_TASK_NAME
            kernel_obj.put(BasisConstants.KERNELOBJ_GLOBAL_TASK_NAME, taskPathFileName);
            //订阅该地址
            subscribeChildChanges(zkClient, path, taskPath, taskPathFileName, kernel_obj, taskName);
            //创建个临时路径，并触发监听事件
            zkClient.createEphemeral(taskPath, DateUtils.getCurrDateTime());
            //建立该任务的消息队列监听
            taskListenerService.addTask(taskName, kernel_obj);
        }
    }

    /**
     * 注册机删除一个任务
     *      1、删除当前任务监听
     *      2、删除当前任务节点
     * @param taskName
     * @param kernel_obj
     */
    public void deleteTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj) {
        String registerFlg = BasisConstants.registerFlg;
        if("true".equals(registerFlg)){
            String businessObjName = (String)kernel_obj.get(BasisConstants.KERNELOBJ_businessObjName);
            String path = createZKTaskListPath(businessObjName, zookeeperGroup);
            //确保永久文件夹存在
            mkZKDirs(zkClient, path);
            //创建临时文件并建立监听
            String taskPathFileName = createZKTaskPathFileName(businessObjName, zookeeperGroup ,taskName);//文件名
            String taskPath = createZKTaskPath(businessObjName, zookeeperGroup, taskName);//全路径

            IZkChildListener iZkChildListener = (IZkChildListener)kernel_obj.get(BasisConstants.KERNELOBJ_IZkChildListener);
            //取消消息队列
            taskListenerService.deleteTask(taskName, kernel_obj);
            //取消监听
            zkClient.unsubscribeChildChanges(path, iZkChildListener);
            zkClient.delete(taskPath);
        }
    }

    /**
     * 注册机更新一个任务
     *      对于zookeeper来说,update无意义
     *      可以通过调用deleteTask和addTask来完成更新
     * @param taskName
     * @param kernel_obj
     */
    public void updateTask(String taskName,ConcurrentHashMap<String,Object> kernel_obj) {

    }
}
