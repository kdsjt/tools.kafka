package com.iecas.kds.tools.kafka.kafkaClient;

import kafka.consumer.KafkaStream;

/**
 * Created by james on 2016/12/19.
 */
public abstract class ConsumerThread implements Runnable{
  protected KafkaStream m_stream;

  public ConsumerThread(KafkaStream m_stream) {
    this.m_stream = m_stream;
  }
}
