package az.courierservice.listener;

import az.courierservice.event.OrderEvent;
import az.courierservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class CourierEventListener {

    private final CourierService courierService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "#{rabbitMQProperties.queues.orderEvents}")
    public void handleOrderEvent(String message) {
        try {
            var event = objectMapper.readValue(message, OrderEvent.class);
            log.info("ActionLog.CourierEventListener - received event, " +
                    "orderId: {}, status: {}", event.getOrderId(), event.getStatus());

            switch (event.getStatus()) {
                case ASSIGNED  -> courierService.handleOrderAssigned(event);
                case DELIVERED -> courierService.handleOrderDelivered(event);
                case CANCELLED -> courierService.handleOrderCancelled(event);
            }
        } catch (Exception ex) {
            log.error("ActionLog.CourierEventListener - failed to process message: {}", message, ex);
        }
    }
}
