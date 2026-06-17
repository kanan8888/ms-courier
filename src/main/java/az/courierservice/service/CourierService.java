package az.courierservice.service;

import az.courierservice.dto.request.CreateCourierRequest;
import az.courierservice.dto.request.UpdateCourierRequest;
import az.courierservice.dto.request.UpdateCourierStatusRequest;
import az.courierservice.dto.response.CourierDeliveryHistoryResponse;
import az.courierservice.dto.response.CourierResponse;
import az.courierservice.enums.Role;
import az.courierservice.event.OrderEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CourierService {

    CourierResponse createCourier(CreateCourierRequest request);
    CourierResponse getCourier(UUID courierId, UUID userId, Role role);
    Page<CourierResponse> getAllCouriers(Pageable pageable);
    List<CourierResponse> getAvailableCouriers();
    CourierResponse updateCourier(UUID courierId, UUID userId, UpdateCourierRequest request);
    CourierResponse updateCourierStatus(UUID courierId, UpdateCourierStatusRequest request);
    void deleteCourier(UUID courierId, UUID adminId);
    Page<CourierDeliveryHistoryResponse> getDeliveryHistory(UUID courierId, UUID userId, Role role, Pageable pageable);
    List<CourierResponse> searchCouriersByName(String name);
    void handleOrderAssigned(OrderEvent event);
    void handleOrderDelivered(OrderEvent event);
    void handleOrderCancelled(OrderEvent event);
}
