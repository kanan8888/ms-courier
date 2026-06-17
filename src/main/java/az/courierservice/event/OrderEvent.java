package az.courierservice.event;

import az.courierservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private UUID orderId;
    private UUID customerId;
    private String customerEmail;
    private UUID courierId;
    private OrderStatus status;
    private BigDecimal deliveryFee;
    private BigDecimal distanceKm;
    private String timestamp;
}
