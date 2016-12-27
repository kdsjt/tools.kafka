package com.iecas.kds.tools.kafka.main;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaClient.ConsumerDataAPI;

/**
 * Created by james on 2016/12/1.
 */
public class KafkaCon {

  public static void main(String[] args) {
    ConsumerDataAPI cdi = new ConsumerDataAPI(Config.conTopicName,Config.conGroupId);
    cdi.getKafkaStreams();
  }
}
