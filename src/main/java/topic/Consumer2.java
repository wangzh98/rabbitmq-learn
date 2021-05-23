package topic;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("topics", "topic");

        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, "topics", "user.*");

        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("这是我们的消费者1" + new String(body));
            }
        });
    }

}
