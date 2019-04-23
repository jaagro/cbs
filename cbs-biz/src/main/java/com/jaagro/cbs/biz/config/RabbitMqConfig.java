package com.jaagro.cbs.biz.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tony
 * @date 2019-04-19
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 获取梵龙环控设备数据队列
     */
    public static final String FAN_LONG_IOT_QUEUE = "fanLong.iot.queue";


    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    /**
     * 创建梵龙环控设备队列
     *
     * @return 队列
     */
    @Bean
    public Queue fanLongIotQueue() {
        return new Queue(FAN_LONG_IOT_QUEUE, true);
    }

    /**
     * Topic模式
     *
     * @return Topic交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * Fanout模式
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     *
     * @return Fanout交换机
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 绑定队列到交换机
     *
     * @return Binding对象
     */
    @Bean
    public Binding locationSendBindingTopic() {
        return BindingBuilder.bind(fanLongIotQueue()).to(topicExchange()).with("fanLong_iot.send");
    }
}
