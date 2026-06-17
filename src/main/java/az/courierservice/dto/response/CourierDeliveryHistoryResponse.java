package az.courierservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CourierDeliveryHistoryResponse {
    private UUID id;
    private UUID orderId;
    private BigDecimal deliveryFee;
    private BigDecimal courierEarning;
    private BigDecimal distanceKm;
    private Instant deliveredAt;
}
