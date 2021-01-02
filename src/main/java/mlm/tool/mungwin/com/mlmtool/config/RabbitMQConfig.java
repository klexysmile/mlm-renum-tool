/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlm.tool.mungwin.com.mlmtool.config;

import mlm.tool.mungwin.com.mlmtool.utils.Parameters;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 *
 * @author D.Wilma
 */
@Configuration
public class RabbitMQConfig {

    private String dlcExchange = Parameters.DLC_EXCHANGE;

    private String memberRegistrationQueue = Parameters.DLC_MEMBER_REGISTRATION_QUEUE;

    private String memberRegistrationExchangeKey = Parameters.DLC_MEMBER_REGISTRATION_EXCHANGE_KEY;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean("rabbit_template")
    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        
        return rabbitTemplate;
    }

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(dlcExchange);
    }

    @Bean
    public Queue newRegistrationQueue() {
        return new Queue(memberRegistrationQueue, true);
    }

    @Bean
    public Binding bindNewRegistrationQueueToExchange() {
        return BindingBuilder
                .bind(newRegistrationQueue())
                .to(topic())
                .with(memberRegistrationExchangeKey);
    }

}
