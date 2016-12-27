import com.iecas.kds.tools.kafka.config.Config;
import com.iecas.kds.tools.kafka.kafkaClient.ConsumerDataAPI;

/**
 * Created by IECAS on 2015/9/24.
 */
public class ComsumerTest {

  public static void main(String[] args) {

    //调用消费者api，需设置要消费的topic名称
    ConsumerDataAPI cdi = new ConsumerDataAPI(Config.conTopicName,"group-kds3");
    cdi.getKafkaStreams();

        /*try {//用户线程休眠，让出cpu执行时间
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    //cdi.shutdown();//关闭连接，释放消费者连接资源和线程资源`
  }
}
