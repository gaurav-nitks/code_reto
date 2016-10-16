package blackbox;

import com.company.miniq.broker.MiniQ;
import com.company.miniq.message.Message;
import org.apache.log4j.Logger;

public class Publisher implements Runnable {
    final static Logger logger = Logger.getLogger("Main");

    private final MiniQ miniQ;

    public Publisher(MiniQ miniQ) {
        this.miniQ = miniQ;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 100; i++) {
            logger.debug("Published- " + miniQ.publish(new Message(("Hello" + i).getBytes())));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
