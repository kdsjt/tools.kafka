package com.iecas.kds.tools.kafka.service;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaClient.ProducerDataByPartitionAPI;
import com.iecas.kds.tools.kafka.kafkaUtils.DataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by james on 2016/12/19.
 */
public class Producer {
  private Map<String, String> map = new HashMap();
  private static AtomicInteger COUNT = new AtomicInteger(1);

  public void producer() {
    ProducerDataByPartitionAPI pd = new ProducerDataByPartitionAPI();
    String name = Thread.currentThread().getName();
    for (int i = 0; i < Config.proCount; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < Config.proBatchSize; j++) {
        pd.sendData(Config.proTopicName, String.valueOf(i * Config.proBatchSize + j), DataSource.getString());
      }
      long time = System.currentTimeMillis() - begin;
      System.out.println(name + "第 " + (i + 1) + " 次发送 " + Config.proBatchSize + " 条数据耗时： " + time + "毫秒,共发送了" + COUNT.getAndIncrement() * Config.proBatchSize + "条数据！");
    }
  }

  public void producer_batch() {
    ProducerDataByPartitionAPI pd = new ProducerDataByPartitionAPI();
    String name = Thread.currentThread().getName();
    for (int i = 0; i < Config.proCount; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < Config.proBatchSize; j++) {
        map.put(String.valueOf(i * Config.proBatchSize + j), DataSource.getString());
      }
      pd.sendDataMap(Config.proTopicName, map);
      map.clear();
      long time = System.currentTimeMillis() - begin;
      System.out.println(name + "第 " + (i + 1) + " 次发送 " + Config.proBatchSize + " 条数据耗时： " + time + "毫秒,共发送了" + COUNT.getAndIncrement() * Config.proBatchSize + "条数据！");
    }
  }
}
