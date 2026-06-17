package az.courierservice.dto.response;

import az.courierservice.enums.CourierStatus;
import az.courierservice.enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CourierResponse {
    private UUID id;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String phone;
    private VehicleType vehicleType;
    private CourierStatus status;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
}
