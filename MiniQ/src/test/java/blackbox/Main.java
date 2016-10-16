package blackbox;

import com.company.miniq.broker.MiniQ;
import com.company.miniq.broker.DefaultMiniQ;
import com.company.miniq.broker.config.Configuration;
import org.apache.log4j.Logger;


public class Main {
    final static Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        MiniQ miniQ = new DefaultMiniQ(new Configuration.ConfigurationBuilder().setAcknowledgeWaitTimeOut(1).build());

        Publisher publisher = new Publisher(miniQ);

        Subscriber subscriber = new Subscriber(miniQ);

        Thread pubThread = new Thread(publisher);

        Thread subThread = new Thread(subscriber);

        pubThread.start();
        subThread.start();

        while (pubThread.isAlive() || subThread.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.debug("BYE BYE");
        miniQ.close();
    }
}
