package cn.kanmars.sn.mq;

import cn.kanmars.sn.utils.BasisConstants;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by baolong on 2016/4/11.
 */
public class Product {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException, UnsupportedEncodingException {
        DefaultMQProducer producer = new DefaultMQProducer(BasisConstants.consumerGroup);

        producer.setInstanceName("finance-payment-instance_001");
        producer.setNamesrvAddr("10.58.50.204:9876;10.58.50.205:9876");
        producer.start();
        producer.setDefaultTopicQueueNums(1);
        for(int i = 0; i<10;i++){
            Message msg = new Message("tpn1",// topic
                    "TagA",// tag
                    "OrderID001",// key
                    ("Hello MetaQ").getBytes("UTF-8"));// body
                    SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    return mqs.get(id);
                }
            },0);
        }
        producer.shutdown();


    }
}
