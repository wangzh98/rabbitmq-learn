package helloworld;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接rabbitmq的主机
        connectionFactory.setHost("localhost");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 获取链接对象
        Connection connection = connectionFactory.newConnection();

        // 获取链接中的通道
        Channel channel = connection.createChannel();

        // 通道绑定消息队列
        // 参数1:队列的名称，如果队列不存在，自动创建
        // 参数2: 用来定义对队列的特性是否需要持久化，true持久化
        // 参数3: exclusive 是否独占队列, true独占
        // 参数4: autoDelete
        // 参数5: 附加参数
        channel.queueDeclare("hello", false, false, false, null);

        // 发布消息
        //  参数1: 交换机名称 参数2: 路由key(队列的名称), 参数3: 传递消息额外设置, 参数4:消息的具体内容
        channel.basicPublish("", "hello",null, "hello rabbitmq".getBytes());

        channel.close();
        connection.close();
    }
}