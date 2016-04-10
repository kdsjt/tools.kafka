import com.iecas.kds.tools.kafka.kafkaClient.ComsumerDataAPI;
import com.iecas.kds.tools.kafka.kafkaClient.ComsumerThreadImpl;
import kafka.consumer.KafkaStream;

import java.util.concurrent.ExecutorService;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ComsumerTest {

    public static void main(String[] args) {

        //调用消费者api，需设置要消费的topic名称
        ComsumerDataAPI cdi = new ComsumerDataAPI("mytest1229","group-1");

        KafkaStream stream = cdi.getKafkaStream();

        ComsumerThreadImpl cti = new ComsumerThreadImpl();
        cti.setM_stream(stream);//这一步必须设置，否则线程不能获取kafka的数据流

        ExecutorService excutor = cdi.getExecutor();//线程调用推介方式
        excutor.submit(cti);

        /*try {//用户线程休眠，让出cpu执行时间
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //cdi.shutdown();//关闭连接，释放消费者连接资源和线程资源

    }
}
