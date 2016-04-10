package com.iecas.kds.tools.kafka.kafkaClient;

import com.iecas.kds.tools.kafka.kafkaUtils.BeanUtils;
import com.iecas.kds.tools.kafka.kafkaUtils.User;
import kafka.consumer.ConsumerIterator;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ComsumerThreadImpl extends IComsumerThread {

    public void run() {

        while (true) {


            if (m_stream != null) {

                ConsumerIterator<byte[], byte[]> it = m_stream.iterator();//进行数据迭代

                int index = 1;
                while (it.hasNext()) {
                    User user = (User) BeanUtils.bytes2Object(it.next().message());
                    System.out.println(index +"/" + user.toString());
                    index++;
                }

            }

        }

    }

}
