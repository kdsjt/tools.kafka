package com.iecas.kds.tools.kafka.kafkaClient;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaUtils.PropertiesUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ConsumerDataAPI {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerDataAPI.class);
  private final int thredNum = Config.conThreadNum;
  private ConsumerConnector consumer;
  private String topic;
  private ExecutorService executor;

  /**
   * 构造函数，初始化信息
   *
   * @param topicName
   */
  public ConsumerDataAPI(String topicName, String groupId) {
    try {
      //初始化
      Properties props = new Properties();
      props.put("zookeeper.connect", PropertiesUtils.get("kafka").getProperty("zookeeper.connect"));
      props.put("group.id", groupId);
      props.put("zookeeper.session.timeout.ms", PropertiesUtils.get("kafka").getProperty("zookeeper.session.timeout.ms"));
      props.put("zookeeper.connection.timeout.ms", PropertiesUtils.get("kafka").getProperty("zookeeper.connection.timeout.ms"));
      props.put("zookeeper.sync.time.ms", PropertiesUtils.get("kafka").getProperty("zookeeper.sync.time.ms"));
      props.put("auto.commit.interval.ms", PropertiesUtils.get("kafka").getProperty("auto.commit.interval.ms"));
      props.put("auto.offset.reset", PropertiesUtils.get("kafka").getProperty("auto.offset.reset"));  //配置是否从头开始读

      this.consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
      this.topic = topicName;
      executor = Executors.newFixedThreadPool(thredNum);

    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("Error get consumer {}", e);
    }
  }

  /**
   * 获取kafka流对象
   *
   * @return
   */
  public void getKafkaStreams() {
    try {
      Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
      topicCountMap.put(topic, new Integer(thredNum));
      Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
        .createMessageStreams(topicCountMap);
      //获取流列表
      List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

      for (final KafkaStream<byte[], byte[]> stream : streams) {
        executor.submit(new ConsumerThreadImpl(stream));
      }
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("Error get stream {}", e);
    }
  }

  /**
   * 关闭连接
   */
  public void shutdown() {
    if (consumer != null)
      consumer.shutdown();
    if (executor != null)
      executor.shutdown();
  }

  public ConsumerConnector getConsumer() {
    return consumer;
  }

  public String getTopic() {
    return topic;
  }

  public ExecutorService getExecutor() {
    return executor;
  }

  public void setExecutor(ExecutorService executor) {
    this.executor = executor;
  }

  public int getThredNum() {
    return thredNum;
  }
}
