package az.courierservice.mapper;

import az.courierservice.dao.entity.Courier;
import az.courierservice.dto.request.CreateCourierRequest;
import az.courierservice.dto.request.UpdateCourierRequest;
import az.courierservice.dto.response.CourierResponse;

public interface CourierMapper {

    static CourierResponse toCourierResponse(Courier courier) {
        return CourierResponse.builder()
                .id(courier.getId())
                .userId(courier.getUserId())
                .firstName(courier.getFirstName())
                .lastName(courier.getLastName())
                .phone(courier.getPhone())
                .vehicleType(courier.getVehicleType())
                .status(courier.getStatus())
                .isActive(courier.isActive())
                .createdAt(courier.getCreatedAt())
                .updatedAt(courier.getUpdatedAt())
                .build();
    }

    static Courier toCourier(CreateCourierRequest request) {
        return Courier.builder()
                .userId(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .vehicleType(request.getVehicleType())
                .build();
    }

    static void updateCourier(UpdateCourierRequest request, Courier courier) {
        courier.setFirstName(request.getFirstName());
        courier.setLastName(request.getLastName());
        courier.setVehicleType(request.getVehicleType());
    }
}
