package com.iecas.kds.tools.kafka.kafkaUtils;

import com.iecas.kds.tools.kafka.config.Config;

import java.util.Random;

/**
 * Created by james on 2016/12/1.
 */
public class DataSource {

  public static String[] strs ;
  private static int strs_length = 10000;
  private static Random random = new Random();

  static {

  }

  public static String[] getStrs(){
    System.out.println("生成数据池开始...");
    strs = new String[strs_length];
    for (int i = 0; i <strs_length ; i++) {
      strs[i] = DataCreater.generateString(Config.proDataSize);
    }
    System.out.println("生成数据池完成！，数据池大小"+strs.length);
    return strs;
  }

  public static String getString (){
    if(null == strs){
      synchronized (DataSource.class){
        if(null == strs){
          getStrs();
        }
      }
    }
    return strs[random.nextInt(strs.length)];
  }
}
