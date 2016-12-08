package com.iecas.kds.tools.kafka.main;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaClient.ProducerDataByPartitionAPI;
import com.iecas.kds.tools.kafka.kafkaUtils.DataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by james on 2016/12/1.
 */
public class KafkaPro {

  private Map<String,String> map = new HashMap();

  public static void main(String[] args) {
    DataSource.getString();
    new KafkaPro().producer();
  }
  public void producer(){
    for (int i = 0; i < Config.proCount; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < Config.proBatchSize; j++) {
        map.put(String.valueOf(i*Config.proBatchSize+j),DataSource.getString());
      }
      ProducerDataByPartitionAPI.sendDataMap(Config.proTopicName,map);
      map.clear();
      long time = System.currentTimeMillis()-begin;
      System.out.println("第 "+(i+1)+" 次发送 "+Config.proBatchSize+" 条数据耗时： "+time+"毫秒,共发送了"+(i+1)*Config.proBatchSize+"条数据！");
    }
  }
}
