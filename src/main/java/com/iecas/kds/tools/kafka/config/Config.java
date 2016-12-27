package com.iecas.kds.tools.kafka.config;

import com.iecas.kds.tools.kafka.kafkaUtils.PropertiesUtils;

/**
 * Created by james on 2016/12/1.
 */
public class Config {

  public static String conTopicName;
  public static String conGroupId;
  public static String proTopicName;
  public static int conThreadNum;
  public static int proThreadNum;
  public static int proDataSize;
  public static int proCount;
  public static int proBatchSize;
  public static int conPrintSize;

  public static String producerType;
  public static String bufferMemory;
  public static String batchMs;
  public static String batchNum;
  public static String maxMessage;

  public static int proBatchSize_kafka;
  public static boolean proIsBatch;

  static {
    conTopicName = PropertiesUtils.get("app").getProperty("con.topic.name");
    conGroupId = PropertiesUtils.get("app").getProperty("con.group.id");
    proTopicName = PropertiesUtils.get("app").getProperty("pro.topic.name");
    producerType = PropertiesUtils.get("kafka").getProperty("producer.type");
    bufferMemory = PropertiesUtils.get("kafka").getProperty("buffer.memory");
    conThreadNum = Integer.parseInt(PropertiesUtils.get("app").getProperty("con.thread.num"));
    proThreadNum = Integer.parseInt(PropertiesUtils.get("app").getProperty("pro.thread.num"));
    proDataSize = Integer.parseInt(PropertiesUtils.get("app").getProperty("pro.data.size"));
    proCount = Integer.parseInt(PropertiesUtils.get("app").getProperty("pro.count"));
    proBatchSize = Integer.parseInt(PropertiesUtils.get("app").getProperty("pro.batch.size"));
    conPrintSize = Integer.parseInt(PropertiesUtils.get("app").getProperty("con.print.size"));
    proBatchSize_kafka = Integer.parseInt(PropertiesUtils.get("kafka").getProperty("pro.batch.size"));
    batchMs = PropertiesUtils.get("kafka").getProperty("queue.buffering.max.ms");
    batchNum = PropertiesUtils.get("kafka").getProperty("batch.num.messages");
    maxMessage = PropertiesUtils.get("kafka").getProperty("queue.buffering.max.messages");
    if(proBatchSize_kafka==1){
      proIsBatch = false;
    }else {
      proIsBatch = true;
    }
  }
}
