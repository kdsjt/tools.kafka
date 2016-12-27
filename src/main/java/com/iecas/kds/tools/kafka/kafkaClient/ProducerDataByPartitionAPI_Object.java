package com.iecas.kds.tools.kafka.kafkaClient;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaUtils.PropertiesUtils;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * kafka生产者接口
 * Created by IECAS on 2015/9/22.
 */
public class ProducerDataByPartitionAPI_Object {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProducerDataAPI.class);
  private static Producer<Object, Object> producer = null;//生产者对象
  private static KeyedMessage<Object, Object> data;//一条消息
  private ArrayList<KeyedMessage<Object, Object>> dataList;//消息集合

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
        props.put("key.serializer.class", PropertiesUtils.get("kafka").getProperty("key.serializer.class_obj"));
        props.put("request.required.acks", PropertiesUtils.get("kafka").getProperty("request.required.acks"));
        props.put("partitioner.class", PropertiesUtils.get("kafka").getProperty("partitioner.class_obj"));
        props.put("serializer.class", PropertiesUtils.get("kafka").getProperty("serializer.class_obj"));
        props.put("producer.type", Config.producerType);
        props.put("buffer.memory",Config.bufferMemory);
        props.put("queue.buffering.max.ms",Config.batchMs);
        props.put("batch.num.messages",Config.batchNum);
        props.put("queue.buffering.max.messages",Config.maxMessage);
        ProducerConfig config = new ProducerConfig(props);
        // 创建producer
        producer = new Producer<Object, Object>(config);
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
  public static void sendData(String topicName, Object key, Object message) {
    try {
      if (producer == null) {
        producer = getProducer();//获取生产者资源
      }
      data = new KeyedMessage<Object, Object>(
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
  public void sendDataList(String topicName, HashMap<Object, Object> messageMap) {
    try {
      if (producer == null) {
        producer = getProducer();//获取生产者资源
      }
      dataList = new ArrayList<KeyedMessage<Object, Object>>();
      KeyedMessage<Object, Object> msg = null;
      int index = 1;//计数器
      for (Map.Entry<Object, Object> message : messageMap.entrySet()) {//循环置入数据
        Object key = message.getKey();
        Object value = message.getValue();
        msg = new KeyedMessage<Object, Object>(topicName, key, value);
        dataList.add(msg);
        if (index % Config.proBatchSize_kafka == 0) {//默认10000条信息发送一次
          producer.send(dataList);
          dataList = new ArrayList<KeyedMessage<Object, Object>>();
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
