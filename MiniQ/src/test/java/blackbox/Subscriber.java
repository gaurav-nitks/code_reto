package blackbox;

import com.company.miniq.broker.Broker;
import com.company.miniq.message.Envelope;

import java.util.List;
import java.util.Random;

public class Subscriber implements Runnable {

    private final Broker broker;

    public Subscriber(Broker broker) {
        this.broker = broker;
    }

    @Override
    public void run() {
        for(int i = 1; i <= 100; i++) {
            List<Envelope> messages = broker.consume();
            for(Envelope e : messages) {

                if(e.isRedelivered()){
                    System.out.println("Consumed "+e.getId()+ " ReDelivered");
                } else {
                    System.out.println("Consumed " + e.getId());
                }

                if(new Random().nextInt(10) == 4 ) {
                    System.out.println("***** Skip ACK for "+e.getId());
                } else {
                    broker.acknowledge(e.getId());
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
