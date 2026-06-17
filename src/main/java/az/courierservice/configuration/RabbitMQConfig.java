package az.courierservice.configuration;

import az.courierservice.configuration.properties.RabbitMQProperties;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue orderEventsQueue(RabbitMQProperties rabbitMQProperties) {
        return QueueBuilder.durable(rabbitMQProperties.getQueues().getOrderEvents()).build();
    }

    @Bean
    public TopicExchange courierExchange(RabbitMQProperties rabbitMQProperties) {
        return new TopicExchange(rabbitMQProperties.getExchange());
    }

    @Bean
    public Binding orderAssignedBinding(RabbitMQProperties rabbitMQProperties) {
        return BindingBuilder
                .bind(orderEventsQueue(rabbitMQProperties))
                .to(courierExchange(rabbitMQProperties))
                .with(rabbitMQProperties.getRoutingKeys().getOrderAssigned());
    }

    @Bean
    public Binding orderDeliveredBinding(RabbitMQProperties rabbitMQProperties) {
        return BindingBuilder
                .bind(orderEventsQueue(rabbitMQProperties))
                .to(courierExchange(rabbitMQProperties))
                .with(rabbitMQProperties.getRoutingKeys().getOrderDelivered());
    }

    @Bean
    public Binding orderCancelledBinding(RabbitMQProperties rabbitMQProperties) {
        return BindingBuilder
                .bind(orderEventsQueue(rabbitMQProperties))
                .to(courierExchange(rabbitMQProperties))
                .with(rabbitMQProperties.getRoutingKeys().getOrderCancelled());
    }
}
