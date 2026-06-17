package az.courierservice.dto.request;

import az.courierservice.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UpdateCourierRequest {

    @NotBlank(message = "{validation.firstName.blank}")
    private String firstName;

    @NotBlank(message = "{validation.lastName.blank}")
    private String lastName;

    @NotNull(message = "{validation.vehicleType.null}")
    private VehicleType vehicleType;
}
