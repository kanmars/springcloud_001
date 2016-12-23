package cn.kanmars.sn.service.impl;

import cn.com.sn.frame.logger.HLogger;
import cn.com.sn.frame.logger.LoggerManager;
import cn.kanmars.sn.cache.SystemConfigInfoCache;
import cn.kanmars.sn.service.TaskListenerService;
import cn.kanmars.sn.utils.BasisConstants;
import cn.kanmars.sn.utils.QueryServerInfo;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.quartz.*;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/4/11.
 */
@Service
public class TaskListenerServiceImpl implements TaskListenerService {

    private HLogger logger = LoggerManager.getLoger("TaskListenerServiceImpl");
    private String mqNotifyAddr = "";
    private String mqNotifyGroup = BasisConstants.MQ_NOTIFY_DEFAULT_GROUP;
    private volatile DefaultMQPushConsumer consumer = null;
    private volatile MessageListenerConcurrently innerMessageListenerConcurrently = null;
    /**
     * 监听初始化
     */
    @PostConstruct
    public void init() {
        //初始化过程
        //1、读取配置文件
        String mqNotifyFlg = BasisConstants.mqNotifyFlg;
        if("true".equals(mqNotifyFlg)){
            String mqNotifyAddr = BasisConstants.mqNotifyAddr;
            this.mqNotifyAddr = mqNotifyAddr;
            String mqNotifyGroup = BasisConstants.mqNotifyGroup;
            this.mqNotifyGroup = mqNotifyGroup;
            String consumerGroup = BasisConstants.consumerGroup;
            //2创建注册核心对象
            Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
            if(consumer==null){
                try {
                    consumer = new DefaultMQPushConsumer(consumerGroup);
                    String topic = BasisConstants.MQ_TOPIC_PREFIX+this.mqNotifyGroup;
                    String tags = "*";

                    consumer.setInstanceName(BasisConstants.MQ_CONSUMERGROUP_PREFIX+BasisConstants.SPLIT_HOSTNAME+ QueryServerInfo.getHostName()+BasisConstants.SPLIT_IP+QueryServerInfo.getIp()+BasisConstants.SPLIT_PID+ QueryServerInfo.getPid());
                    consumer.setNamesrvAddr(this.mqNotifyAddr);
                    //consumer.setHeartbeatBrokerInterval(30*1000);//心跳时间，单位毫秒,默认为30*1000毫秒
                    //consumer.setPullInterval(0);//消息拉取时间，单位毫秒,默认为30*1000毫秒
                    //consumer.setPullThresholdForQueue(1000);//消息的本地储存数量//默认为1000
                    consumer.setConsumeMessageBatchMaxSize(1);//监听器每次接受本地队列的消息是多少条，默认为1
                    //consumer.setPersistConsumerOffsetInterval(5000);//消费进度储存时间间隔，默认为5*1000毫秒
                    consumer.setConsumeThreadMin(1);//设置消费线程数1，用于顺序消费
                    consumer.setConsumeThreadMax(1);//设置消费线程数1，用于顺序消费
                    consumer.setMessageModel(MessageModel.BROADCASTING);//广播消费模式
                    consumer.subscribe(topic, tags);//订阅主题，标签
                    innerMessageListenerConcurrently = new InnerMessageListenerConcurrently();
                    consumer.registerMessageListener(innerMessageListenerConcurrently);
                    consumer.start();
                } catch (MQClientException e) {
                    logger.error("MQ消息队列监听创建失败",e);
                    consumer=null;
                }
            }
            //4创建注册核心与远程链接同步任务
            for(Map.Entry<String,ConcurrentHashMap<String,Object>> e : kernel.entrySet()){
                String taskName = e.getKey();
                ConcurrentHashMap<String,Object> kernel_obj = e.getValue();
                addTask(taskName, kernel_obj);
            }
        }
    }

    /**
     * 监听销毁
     */
    @PreDestroy
    public void destory() {
        //1、读取配置文件
        String mqNotifyFlg = BasisConstants.mqNotifyFlg;
        if("true".equals(mqNotifyFlg)){
            String mqNotifyAddr = BasisConstants.mqNotifyAddr;
            String mqNotifyGroup = BasisConstants.mqNotifyGroup;
            this.mqNotifyGroup = mqNotifyGroup;
            String consumerGroup = BasisConstants.consumerGroup;
            //2创建注册核心对象
            Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
            //4创建注册核心与远程链接同步任务
            for(Map.Entry<String,ConcurrentHashMap<String,Object>> e : kernel.entrySet()){
                String taskName = e.getKey();
                ConcurrentHashMap<String,Object> kernel_obj = e.getValue();
                deleteTask(taskName, kernel_obj);
            }
            //3关闭监听
            consumer.shutdown();
        }
    }

    /**
     * 监听添加一个任务，并更新kernel_obj
     *
     * @param taskName
     * @param kernel_obj
     */
    public synchronized void addTask(String taskName, ConcurrentHashMap<String, Object> kernel_obj) {
        kernel_obj.put(BasisConstants.KERNELOBJ_MQ_CONSUMER, consumer);
    }

    /**
     * 监听删除一个任务，并更新kernel_obj
     *
     * @param taskName
     * @param kernel_obj
     */
    public synchronized void deleteTask(String taskName, ConcurrentHashMap<String, Object> kernel_obj) {
        kernel_obj.remove(BasisConstants.KERNELOBJ_MQ_CONSUMER);
    }

    /**
     * 监听更新一个任务，并更新kernel_obj
     *       对于rocketmq来说,update无意义
     * @param taskName
     * @param kernel_obj
     */
    public synchronized void updateTask(String taskName, ConcurrentHashMap<String, Object> kernel_obj) {
//        deleteTask(taskName_old,kernel_obj);
//        addTask(taskName_new,kernel_obj);
    }

    class InnerMessageListenerConcurrently implements MessageListenerConcurrently{

        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("接收到触发消息");
            try {
                MessageExt msg = msgs.get(0);
                String topic = msg.getTopic();//获取标题
                String tags = msg.getTags();//获取标签//tags为GLOBALTASKNAME即//businessObjName+"_HOSTNAME"+getHostName()+"_IP"+getIp()+"_PID"+getPid()+"_TASKNAME"+taskname;
                int idx_taskName = tags.lastIndexOf(BasisConstants.SPLIT_TASKNAME);
                if(idx_taskName<0){
                    logger.info("消息不符合规范结束");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String taskName = tags.substring(tags.lastIndexOf(BasisConstants.SPLIT_TASKNAME) + 9);
                ConcurrentHashMap<String,Object> kernel_obj = SystemConfigInfoCache.getTaskInnerInfos().get(taskName);
                String keys = msg.getKeys();//获取key
                byte[] body = msg.getBody();//获取报文体
                //判断tags在当前进程是否存在
                Map<String,ConcurrentHashMap<String,Object>> kernel = SystemConfigInfoCache.getTaskInnerInfos();
                boolean isCurrentTaskTrigedMsg = false;

                for(Map.Entry<String,ConcurrentHashMap<String,Object>> e : kernel.entrySet()){
                    ConcurrentHashMap<String,Object> kernel_object = e.getValue();
                    String GLOBAL_TASK_NAME = (String)kernel_object.get(BasisConstants.KERNELOBJ_GLOBAL_TASK_NAME);
                    String GLOBAL_TASK_NAME_ = GLOBAL_TASK_NAME.replaceAll("\\.","_");
                    if(tags.equals(GLOBAL_TASK_NAME_)){
                        isCurrentTaskTrigedMsg = true;
                        break;
                    }
                }
                if(!isCurrentTaskTrigedMsg){
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                logger.info("触发消息为当前进程中的任务topic["+topic+"]tags["+tags+"]keys["+keys+"]body["+body+"]");
                ChildJobTimerRunImpl childJobTimerRunImpl = ChildJobTimerRunImpl.class.newInstance();
                JobDetail jobDetail = JobBuilder.newJob(ChildJobTimerRunImpl.class).withIdentity(taskName, BasisConstants.CHILD_TASK_RUN).build();
                jobDetail.getJobDataMap().put("topic", topic);
                jobDetail.getJobDataMap().put("tags", tags);
                jobDetail.getJobDataMap().put("keys", keys);
                jobDetail.getJobDataMap().put("body", body);
                jobDetail.getJobDataMap().put("TaskBasicConfig", kernel_obj.get("TaskBasicConfig"));
                JobExecutionContextImpl jec = new JobExecutionContextImpl(null,new TriggerFiredBundle(jobDetail,new SimpleTriggerImpl(),null,true,null,null,null,null),null);
                childJobTimerRunImpl.execute(jec);
            } catch (Exception e) {
                logger.error("消息触发执行异常",e);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
