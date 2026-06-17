package az.courierservice.dto.request;

import az.courierservice.enums.CourierStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class UpdateCourierStatusRequest {

    @NotNull(message = "{validation.status.null}")
    private CourierStatus status;
}
