package az.courierservice.mapper;

import az.courierservice.dao.entity.Courier;
import az.courierservice.dao.entity.CourierDeliveryHistory;
import az.courierservice.dto.response.CourierDeliveryHistoryResponse;
import az.courierservice.event.OrderEvent;

import java.math.BigDecimal;

public interface DeliveryHistoryMapper {

    static CourierDeliveryHistoryResponse toDeliveryHistoryResponse(CourierDeliveryHistory history) {
        return CourierDeliveryHistoryResponse.builder()
                .id(history.getId())
                .orderId(history.getOrderId())
                .deliveryFee(history.getDeliveryFee())
                .courierEarning(history.getCourierEarning())
                .distanceKm(history.getDistanceKm())
                .deliveredAt(history.getDeliveredAt())
                .build();
    }

    static CourierDeliveryHistory toDeliveryHistory(OrderEvent event, Courier courier, BigDecimal earning) {
        return CourierDeliveryHistory.builder()
                .courier(courier)
                .orderId(event.getOrderId())
                .deliveryFee(event.getDeliveryFee())
                .courierEarning(earning)
                .distanceKm(event.getDistanceKm())
                .build();
    }
}
