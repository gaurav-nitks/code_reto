package com.company.miniq.broker;

import com.company.miniq.broker.config.Configuration;
import com.company.miniq.message.Envelope;
import com.company.miniq.message.Message;
import com.company.miniq.message.MessageId;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class DefaultMiniQTest {
    private static MiniQ miniQ;

    @BeforeClass
    public static void init() {
        miniQ = new DefaultMiniQ(new Configuration.ConfigurationBuilder().setAcknowledgeWaitTimeOut(1).build());
    }

    @Test
    public void testPublish() {
        MessageId id = miniQ.publish(new Message("Test Message1".getBytes()));
        Assert.assertEquals(id.getId(), 1L);

        id = miniQ.publish(new Message("Test Message2".getBytes()));
        Assert.assertEquals(id.getId(), 2L);
    }

    @Test(dependsOnMethods = {"testPublish"})
    public void testConsume() {
        List<Envelope> messages = miniQ.consume();

        Assert.assertNotNull(messages);
        Assert.assertEquals(messages.size(), 2);
        Assert.assertEquals(messages.get(0).getId().getId(), 1);
        miniQ.acknowledge(messages.get(0).getId());

        Assert.assertEquals(messages.get(1).getId().getId(), 2);
    }

    @Test(dependsOnMethods = {"testConsume"})
    public void testAck() {
        // Sleep for 2 sec for unacknowledged messages to be available for consumption.
        sleep();

        List<Envelope> messages = miniQ.consume();

        Assert.assertNotNull(messages);
        Assert.assertEquals(messages.size(), 1);
        Assert.assertEquals(messages.get(0).getId().getId(), 2);
        Assert.assertTrue(messages.get(0).isRedelivered());

        miniQ.acknowledge(messages.get(0).getId());
    }

    private void sleep(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
