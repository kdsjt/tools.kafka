package com.iecas.kds.tools.kafka.kafkaClient;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ConsumerThreadImpl implements Runnable {

  private KafkaStream m_stream;

  public ConsumerThreadImpl(KafkaStream m_stream) {
    this.m_stream = m_stream;
  }

  public void run() {
    while (true) {
      if (m_stream != null) {
        try {
          ConsumerIterator<byte[], byte[]> it = m_stream.iterator();//进行数据迭代
          int index = 1;
          String name = Thread.currentThread().getName();
          while (it.hasNext()) {
//          User user = (User) BeanUtils.bytes2Object(it.next().message());
//          System.out.println(index +"/" + user.toString());
            if(index%10000==0){
              System.out.println(Thread.currentThread().getName() + " 发送的第: "+index+"条数据" + new String(it.next().message(), "utf-8"));
            }else {
              it.next().message();
            }
            index++;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
