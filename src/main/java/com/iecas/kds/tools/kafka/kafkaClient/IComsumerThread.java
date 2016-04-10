package com.iecas.kds.tools.kafka.kafkaClient;

import kafka.consumer.KafkaStream;

/**
 * 消费者线程接口，接收数据后处理逻辑可在run方法中执行
 * Created by IECAS on 2015/9/24.
 */
public abstract class IComsumerThread implements Runnable {

    protected KafkaStream m_stream;

    public KafkaStream getM_stream() {
        return m_stream;
    }

    public void setM_stream(KafkaStream m_stream) {
        this.m_stream = m_stream;
    }

    public void run() {}

}
