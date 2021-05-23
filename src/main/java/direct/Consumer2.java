package direct;

import com.rabbitmq.client.*;
import utils.RabbitmqUtils;

import java.io.IOException;

public class Consumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitmqUtils.connection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare("logs_direct", "direct");

        //创建临时队列
        String queue = channel.queueDeclare().getQueue();

        // 基于路由key绑定队列和交换机
        channel.queueBind(queue, "logs_direct", "error");

        // 获取消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1:" + new String(body));
            }
        });

    }
}
