package cn.kanmars.sn.utils;

import cn.com.xcommon.frame.logger.HLogger;
import cn.com.xcommon.frame.logger.LoggerManager;
import cn.com.xcommon.frame.util.StringUtils;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by baolong on 2016/4/14.
 */
public class TaskTrigerUtils {

    public static final HLogger logger = LoggerManager.getLoger("TaskTrigerUtils");

    protected static ConcurrentHashMap<String,DefaultMQProducer> producerMap  = new ConcurrentHashMap<String,DefaultMQProducer>();

    public static final String charset = "utf-8";

    public synchronized static boolean trigerTask(String mqProducerGroup,String instanceName,String nameServerAddr,String mqNotifyGroup,String businessObjName,String globalTaskName,String key,String message){
        try{
            DefaultMQProducer producer = producerMap.get(mqProducerGroup);
            if(producer == null){
                producer = new DefaultMQProducer(mqProducerGroup);
                producer.setInstanceName(instanceName);
                producer.setNamesrvAddr(nameServerAddr);
                producer.start();
                producerMap.put(mqProducerGroup,producer);
            }
            if(StringUtils.isEmpty(message)){
                message= "NULLMESSAGE";
            }
            for(int i = 0; i<1;i++){
                String topic = "TASK_TRIGER_TOPIC_"+mqNotifyGroup;
                String tags = globalTaskName.replaceAll("\\.","_");
                Message msg = new Message(topic,// topic
                        tags,// tags
                        key,// key
                        message.getBytes(charset));// body
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        return mqs.get(id);
                    }
                },0);
            }
            return true;
        }catch (Exception e){
            logger.error("任务触发异常",e);
            return false;
        }
    }


    public static void main(String[] args) throws MQClientException {
        String mqProducerGroup = "finance-payment";
        String instanceName = "finance-payment-instance_001";
        String nameServerAddr = "10.58.50.204:9876;10.58.50.205:9876";
        String mqNotifyGroup = "UAT_GROUP";
        String businessObjName = "CPSRateSuccessOrderSyncTask_HOSTNAMEBAOLONG_IP10_144_33_185_PID9112_TASKNAME37_0";
        String globalTaskName = "2";//调用第N个全局任务
        String key = "k";
        String message = "msg";
        boolean trigerIsSuccess = TaskTrigerUtils.trigerTask(mqProducerGroup,instanceName,nameServerAddr,mqNotifyGroup,businessObjName,globalTaskName,key,message);
        logger.info("触发结果"+(trigerIsSuccess?"[成功]":"[失败]"));
    }
}
