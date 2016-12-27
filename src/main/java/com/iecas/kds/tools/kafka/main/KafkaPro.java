package com.iecas.kds.tools.kafka.main;

import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaUtils.DataSource;
import com.iecas.kds.tools.kafka.service.Producer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by james on 2016/12/1.
 */
public class KafkaPro {

  public static void main(String[] args) {
    AtomicInteger flag_end = new AtomicInteger(0);
    DataSource.getString();
    ExecutorService pool = Executors.newCachedThreadPool();
    long begin = System.currentTimeMillis();
    for (int i = 0; i < Config.proThreadNum; i++) {
      pool.execute(() -> {
        flag_end.getAndIncrement();
        if (Config.producerType.equals("async")) {
          new Producer().producer();
        } else if (!Config.proIsBatch) {
          new Producer().producer();
        } else {
          new Producer().producer_batch();
        }
        flag_end.getAndDecrement();
      });
    }
    while (true) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (flag_end.get() == 0) {
        break;
      }
    }
    pool.shutdownNow();
    long time = System.currentTimeMillis() - begin;
    int second = (int) (time/1000);
    int throughput = Config.proCount*Config.proBatchSize*Config.proThreadNum/second;
    System.out.println("共耗时： "+time + " ms！\n吞吐为： "+ throughput +" 条消息每秒");
  }
}
