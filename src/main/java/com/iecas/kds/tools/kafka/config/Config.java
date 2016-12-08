package com.iecas.kds.tools.kafka.config;

import com.iecas.kds.tools.kafka.kafkaUtils.PropertiesUtils;

/**
 * Created by james on 2016/12/1.
 */
public class Config {

  public static String conTopicName;
  public static String proTopicName;
  public static String groupId;
  public static int conThreadNum;
  public static int proDataSize;
  public static int proCount;
  public static int proBatchSize;

  static {
    conTopicName = PropertiesUtils.get("kafka").getProperty("con.topic.name");
    proTopicName = PropertiesUtils.get("kafka").getProperty("pro.topic.name");
    groupId = PropertiesUtils.get("kafka").getProperty("group.id");
    conThreadNum = Integer.parseInt(PropertiesUtils.get("kafka").getProperty("con.thread.num"));
    proDataSize = Integer.parseInt(PropertiesUtils.get("kafka").getProperty("pro.data.size"));
    proCount = Integer.parseInt(PropertiesUtils.get("kafka").getProperty("pro.count"));
    proBatchSize = Integer.parseInt(PropertiesUtils.get("kafka").getProperty("pro.batch.size"));

  }

}
