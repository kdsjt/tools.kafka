package com.iecas.kds.tools.kafka.kafkaClient;

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
public class ComsumerDataAPI {


    private ConsumerConnector consumer;
    private String topic;
    private ExecutorService executor;
    private final int thredNum =1;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComsumerDataAPI.class);

    /**
     * 构造函数，初始化信息
     * @param topicName
     */
    public ComsumerDataAPI(String topicName,String groupId) {

        try {
            //初始化
            Properties props = new Properties();
            props.put("zookeeper.connect", PropertiesUtils.application.getProperty("zookeeper.connect"));
            props.put("group.id",groupId);
            props.put("zookeeper.session.timeout.ms", PropertiesUtils.application.getProperty("zookeeper.session.timeout.ms"));
            props.put("zookeeper.connection.timeout.ms", PropertiesUtils.application.getProperty("zookeeper.connection.timeout.ms"));
            props.put("zookeeper.sync.time.ms", PropertiesUtils.application.getProperty("zookeeper.sync.time.ms"));
            props.put("auto.commit.interval.ms", PropertiesUtils.application.getProperty("auto.commit.interval.ms"));

            this.consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
            this.topic = topicName;
            executor = Executors.newFixedThreadPool(thredNum);

        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error get consumer {}", e);
        }

    }

    /**
     * 获取kafka流对象
     * @return
     */
    public KafkaStream getKafkaStream(){

        try {

            Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
            topicCountMap.put(topic, new Integer(thredNum));//设定只用一个流

            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer
                    .createMessageStreams(topicCountMap);

            //获取流列表
            List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

            if (streams.size()>0){
                KafkaStream stream = streams.get(0);
                return stream;
            }else{
                return null;
            }

        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error get stream {}", e);
        }

        return null;

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
