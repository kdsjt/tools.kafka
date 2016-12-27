package com.iecas.kds.tools.kafka.partition;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * 用于自定义分区分区规则
 * Created by Administrator on 2016/1/5.
 */
public class MyPartitioner_Object implements Partitioner {

  public MyPartitioner_Object(VerifiableProperties props) {

  }

  /**
   * @param o 发送数据的key对象，本例中是一个String对象
   * @param i 分区的count
   * @return 返回分区的序号，从0 -（i-1）
   */
  @Override
  public int partition(Object o, int i) {

    try {
      int partition;
      int m = (Integer) o;
      partition = Math.abs(m % i);
      return partition;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return Math.abs(o.hashCode() % i);
    }
  }
}
