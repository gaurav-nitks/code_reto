package blackbox;

import com.company.miniq.broker.Broker;
import com.company.miniq.message.Message;

public class Publisher implements Runnable {
    private final Broker broker;

    public Publisher(Broker broker) {
        this.broker = broker;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 100; i++) {
            System.out.println("Published- "+broker.publish(new Message(("Hello" + i).getBytes())));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
