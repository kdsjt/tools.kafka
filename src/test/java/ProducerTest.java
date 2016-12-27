import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaClient.ProducerDataAPI;

import java.util.ArrayList;

/**
 * Created by IECAS on 2015/9/23.
 */
public class ProducerTest {
  public static int flag = 1;

  public static void main(String[] args) {

    String[] str = new String[]{
      "how are you",
      "hello beijing",
      "who are you",
      "my name is li lei",
      "i come from beijing"
    };

    int m = 100;             //循环发送多少次指定字符串数组
    int n = 100;              //多少条字符串发送一次
    int x = 10000;              //多少条字符串数据进行一次统计信息的输出
    int y = str.length;     //一个字符串数组的长度
    String topicName = Config.proTopicName;

    ArrayList<String> list = new ArrayList();
    // HashMap map = new HashMap();
    long begin = System.currentTimeMillis();
    int index = 0;
    long end = 0l;

    for (int j = 0; j < m; j++) {
      for (int i = 0; i < y; i++) {
        // map.put(flag+"",str[i]);
        list.add(flag + " " + str[i]);
        flag++;
        //ProducerDataAPI.sendData("topic1",str[i]);
        //System.out.println(str[i]);
      }
      index += y;
      if (index % n == 0) {
        //ProducerDataByPartitionAPI.sendDataMap(topicName, map);
        new ProducerDataAPI().sendDataList(topicName, list);
        //map.clear();
        list.clear();
        try {
          Thread.sleep(2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      if (index % x == 0) {
        end = System.currentTimeMillis();
        long time = end - begin;
        System.out.println("------已发送：" + index + "条数据------" + "耗时：" + time / 1000);

      }
    }
    end = System.currentTimeMillis();
    long time = end - begin;
    System.out.println(m * y + "条数据耗时" + time / 1000 + "秒\nTPS：" + m * y / (time / 1000));
  }
  /**
   public static void main(String[] args) {

   long bgTime = System.currentTimeMillis();

   new Thread(new Runnable() {
  @Override public void run() {
  for (int i = 1; i <= 1000; i++) {

  ProducerDataAPI.sendData("test001",i+"");
  System.out.println(i);

  }
  }
  }).start();

   new Thread(new Runnable() {
  @Override public void run() {
  for (int i = 1; i <= 100; i++) {

  User user = new User();
  user.setName("name#"+i);
  user.setAge(i);
  HashMap<Object,Object> map = new HashMap<>();

  if(i%10==0){
  map.put(i+"",user);
  ProducerDataByPartitionAPI_Object.sendDataList("topic",map);
  map = new HashMap<>();
  }else {
  map.put(i+"",user);
  }

  System.out.println(i);

  }
  }
  }).start();
   int index = (int)Math.pow(10, 8);

   for (int i = 1; i <= 1000; i++) {

   ProducerDataAPI.sendData("test001",i+"");
   System.out.println(i);

   }


   for (int i = 1; i <= 100; i++) {

   User user = new User();
   user.setName("name#"+i);
   user.setAge(i);
   HashMap<Object,Object> map = new HashMap<>();

   if(i%10==0){
   map.put(i+"",user);
   ProducerDataByPartitionAPI_Object.sendDataList("topic",map);
   map = new HashMap<>();
   }else {
   map.put(i+"",user);
   }

   System.out.println(i);
   }

   ArrayList<Object> messageList = new ArrayList<>();
   User user = new User();

   for (int i = 1; i <= 10000; i++) {
   user.setName(i+"##"+"");
   user.setAge(i);
   messageList.add(user);

   ProducerDataAPI_Object.sendDataList("test1229",messageList);
   messageList = new ArrayList<>();
   System.out.println("addtplist:"+i);
   }

   ProducerDataAPI_Object.closeProducer();

   long endTime = System.currentTimeMillis();

   System.out.println("useTime:"+(endTime-bgTime));

   }
   */
}
