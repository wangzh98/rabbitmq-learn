package workqueue;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.connection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1" + new String(body));
            }
        });

    }
}
