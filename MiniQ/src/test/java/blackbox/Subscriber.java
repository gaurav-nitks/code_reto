package blackbox;

import com.company.miniq.broker.MiniQ;
import com.company.miniq.message.Envelope;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Random;

public class Subscriber implements Runnable {
    final static Logger logger = Logger.getLogger("Subscriber");

    private final MiniQ miniQ;

    public Subscriber(MiniQ miniQ) {
        this.miniQ = miniQ;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 100; i++) {
            List<Envelope> messages = miniQ.consume();
            for(Envelope e : messages) {

                if(e.isRedelivered()){
                    logger.debug("Consumed- "+e.getId()+ " ReDelivered");
                } else {
                    logger.debug("Consumed- " + e.getId());
                }

                if(new Random().nextInt(10) == 4 ) {
                    logger.debug("** Skip ACK for messageId" + e.getId());
                } else {
                    miniQ.acknowledge(e.getId());
                }
            }
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
