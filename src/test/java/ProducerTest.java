import com.iecas.kds.tools.kafka.kafkaClient.ProducerDataAPI;

import java.util.ArrayList;

/**
 * Created by IECAS on 2015/9/23.
 */
public class ProducerTest {

    public static void main(String[] args) {
        String[] str = new String[]{
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you",
                "my name lilei",
                "how are you",
                "hello bei jing",
                "who are you"
        };
        ArrayList<String> list = new ArrayList();
        long begin = System.currentTimeMillis();
        int index=0;
        long end = 0l ;

        for (int j = 0; j < 4000000; j++) {
            for (int i = 0; i < str.length; i++) {
                list.add(str[i]);
                //ProducerDataAPI.sendData("topic1",str[i]);
                //System.out.println(str[i]);
            }
            index+=50;
            if(index%250==0){
                ProducerDataAPI.sendDataList("topic1", list);
                list = new ArrayList<>();
            }
            if(index%1000000==0){
                end = System.currentTimeMillis();
                long time = end-begin;
                System.out.println("------已发送："+index+"条数据------"+"耗时："+time/1000);

            }
        }
        end = System.currentTimeMillis();
        long time = end-begin;
        System.out.println("20000w条数据耗时"+time/1000+"秒\nTPS："+200000000/(time/1000));
    }
//    public static void main(String[] args) {
//
//        long bgTime = System.currentTimeMillis();
//
//
//       // int index = (int)Math.pow(10, 8);
//
//        /*for (int i = 1; i <= 10000; i++) {
//
//            ProducerDataAPI.sendData("test001",i+"");
//            System.out.println(i);
//
//        }*/
//        for (int i = 1; i <= 10000; i++) {
//
//            User user = new User();
//            user.setName("name#"+i);
//            user.setAge(i);
//
//            ProducerDataAPI_Object.sendData("mytest1229",user);
//            System.out.println(i);
//
//        }
//
//       /* ArrayList<Object> messageList = new ArrayList<>();
//        User user = new User();
//
//        for (int i = 1; i <= 10000; i++) {
//            user.setName(i+"##"+"");
//            user.setAge(i);
//            messageList.add(user);
//
//            ProducerDataAPI_Object.sendDataList("test1229",messageList);
//            messageList = new ArrayList<>();
//            System.out.println("addtplist:"+i);
//        }*/
//
//        ProducerDataAPI_Object.closeProducer();
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("useTime:"+(endTime-bgTime));
//
//    }

}
