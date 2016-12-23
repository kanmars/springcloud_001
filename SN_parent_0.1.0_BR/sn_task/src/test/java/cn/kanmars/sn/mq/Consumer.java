package cn.kanmars.sn.mq;

import java.util.List;

import cn.kanmars.sn.utils.BasisConstants;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * Created by baolong on 2016/4/12.
 */
public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(BasisConstants.consumerGroup);
        consumer.setInstanceName("finance-payment-instance_001");
        consumer.setNamesrvAddr("10.58.50.204:9876;10.58.50.205:9876");
        //consumer.setHeartbeatBrokerInterval(30*1000);//心跳时间，单位毫秒,默认为30*1000毫秒
        //consumer.setPullInterval(0);//消息拉取时间，单位毫秒,默认为30*1000毫秒
        //consumer.setPullThresholdForQueue(1000);//消息的本地储存数量//默认为1000
        //consumer.setConsumeMessageBatchMaxSize(1);//监听器每次接受本地队列的消息是多少条，默认为1
        //consumer.setPersistConsumerOffsetInterval(5000);//消费进度储存时间间隔，默认为5*1000毫秒
        consumer.setConsumeThreadMin(1);//设置消费线程数1
        consumer.setConsumeThreadMax(1);//设置消费线程数1
        consumer.subscribe("tpn1", "TagA||TagB||TagC||TagD");//订阅主题，标签
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext arg1) {
                MessageExt msg = msgs.get(0);
                String topics = msg.getTopic();//获取标题
                String tags = msg.getTags();//获取标签
                String keys = msg.getKeys();//获取key
                byte[] body = msg.getBody();//获取报文体
                // 有异常记得抛出来，不要全捕获了，这样保证不能消费的消息下次重推，每次重新消费间隔：10s,30s,1m,2m,3m
                // 注意上述处理不要把异常捕了，如果没有异常会认为都成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

        });
        consumer.start();
    }
}
