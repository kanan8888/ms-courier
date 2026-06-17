package az.courierservice.dto.request;

import az.courierservice.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@NoArgsConstructor
@ToString
public class CreateCourierRequest {

    @NotNull(message = "{validation.userId.null}")
    private UUID userId;

    @NotBlank(message = "{validation.firstName.blank}")
    private String firstName;

    @NotBlank(message = "{validation.lastName.blank}")
    private String lastName;

    @NotBlank(message = "{validation.phone.blank}")
    @Pattern(regexp = "^\\+994[0-9]{9}$", message = "{validation.phone.invalid}")
    private String phone;

    @NotNull(message = "{validation.vehicleType.null}")
    private VehicleType vehicleType;
}
