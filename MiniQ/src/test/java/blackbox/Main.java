package blackbox;

import com.company.miniq.broker.Broker;
import com.company.miniq.broker.DefaultBroker;
import com.company.miniq.broker.config.Configuration;
import com.company.miniq.scheduler.Scheduler;


public class Main {
    public static void main(String[] args) {
        Broker broker = new DefaultBroker(new Configuration.ConfigurationBuilder().setAcknowledgeWaitTimeOut(1).build());

        Publisher p = new Publisher(broker);

        Subscriber s = new Subscriber(broker);

        Thread pt = new Thread(p);

        Thread ct = new Thread(s);

        pt.start();
        ct.start();

        while (pt.isAlive() || ct.isAlive()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("BYE BYE");
        broker.close();
    }
}
