package az.courierservice.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.rabbitmq")
public class RabbitMQProperties {
    private String exchange;
    private Queues queues;
    private RoutingKeys routingKeys;

    @Getter
    @Setter
    public static class Queues {
        private String orderEvents;
    }

    @Getter
    @Setter
    public static class RoutingKeys {
        private String orderAssigned;
        private String orderDelivered;
        private String orderCancelled;
    }
}
