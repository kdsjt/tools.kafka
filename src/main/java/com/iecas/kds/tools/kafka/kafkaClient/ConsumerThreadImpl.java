package com.iecas.kds.tools.kafka.kafkaClient;

import com.iecas.kds.tools.kafka.config.Config;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ConsumerThreadImpl extends ConsumerThread {

  public ConsumerThreadImpl(KafkaStream m_stream) {
    super(m_stream);
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
            if (index % Config.conPrintSize == 0) {
              String data = new String(it.next().message(), "utf-8");
              if(data.length()>50){
                data = data.substring(0,40);
              }
              System.out.println(Thread.currentThread().getName() + " 发送的第: " + index + "条数据" + data);
            } else {
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
