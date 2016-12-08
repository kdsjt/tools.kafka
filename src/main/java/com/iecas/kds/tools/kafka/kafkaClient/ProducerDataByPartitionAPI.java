package com.iecas.kds.tools.kafka.kafkaClient;

import com.iecas.kds.tools.kafka.kafkaUtils.PropertiesUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * kafka生产者接口
 * Created by IECAS on 2015/9/22.
 */
public class ProducerDataByPartitionAPI {

    private static Producer<String, String> producer = null;//生产者对象
    private static KeyedMessage<String, String> data;//一条消息
    private static ArrayList<KeyedMessage<String, String>> dataList;//消息集合
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDataByPartitionAPI.class);


    public static void main(String[] args) {
        System.out.println(PropertiesUtils.get("kafka").getProperty("metadata.broker.list"));
        System.out.println(PropertiesUtils.get("kafka").getProperty("serializer.class"));
    }

    /**
     * 获取生产者对象
     *
     * @return
     */
    public static Producer getProducer() {

        try {

            if (producer == null) {//第一次调用生产者，进行生产者的初始化操作

                // 设置配置属性
                Properties props = new Properties();
                props.put("metadata.broker.list", PropertiesUtils.get("kafka").getProperty("metadata.broker.list"));
                props.put("serializer.class", PropertiesUtils.get("kafka").getProperty("serializer.class"));
                // key.serializer.class默认为serializer.class
                props.put("key.serializer.class", PropertiesUtils.get("kafka").getProperty("key.serializer.class"));
                // 触发acknowledgement机制，否则是fire and forget，可能会引起数据丢失
                // 值为0,1,-1,可以参考
                // http://kafka.apache.org/08/configuration.html
                props.put("request.required.acks", PropertiesUtils.get("kafka").getProperty("request.required.acks"));
                props.put("partitioner.class", PropertiesUtils.get("kafka").getProperty("partitioner.class"));
                ProducerConfig config = new ProducerConfig(props);

                // 创建producer
                producer = new Producer<String, String>(config);

            }

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error get producer{}", e);
        }

        return producer;

    }

    /**
     * 释放生产者资源
     */
    public static void closeProducer() {

        if (producer != null) {
            producer.close();
            producer = null;
        }

    }


    /**
     * 向kafka消息队列指定topic发送一条消息
     *
     * @param topicName topic名称
     * @param message   消息
     */
    public static void sendData(String topicName, String key, String message) {

        try {

            if (producer == null) {
                producer = getProducer();//获取生产者资源
            }

            data = new KeyedMessage<String, String>(
                    topicName, key, message);
            producer.send(data);//发送消息

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error produce to {},message is {}", topicName, message, e);
        }


    }

    /**
     * 向kafka消息队列指定topic发送一组消息
     *
     * @param topicName
     * @param messageMap
     */
    public static void sendDataMap(String topicName, Map<String, String> messageMap) {

        try {

            if (producer == null) {
                producer = getProducer();//获取生产者资源
            }

            dataList = new ArrayList<KeyedMessage<String, String>>();

            KeyedMessage<String, String> msg = null;

            int index = 1;//计数器

            for (Entry<String, String> entry : messageMap.entrySet()) {//循环置入数据

                String key = entry.getKey();
                String value = entry.getValue();
                msg = new KeyedMessage<String, String>(topicName, key, value);

                dataList.add(msg);

                if (index % 10000 == 0) {//默认10000条信息发送一次
                    producer.send(dataList);
                    dataList = new ArrayList<KeyedMessage<String, String>>();
                    index++;
                } else {
                    index++;
                }

                msg = null;

            }

            //将最后的消息刷入kafka
            producer.send(dataList);

        } catch (Exception e) {

            e.printStackTrace();
            LOGGER.error("Error produce to {},messageListsize is {}", topicName, messageMap.size(), e);

        }

    }


}
