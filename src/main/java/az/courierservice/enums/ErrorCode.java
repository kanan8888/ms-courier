package az.courierservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COURIER_NOT_FOUND(HttpStatus.NOT_FOUND, "courier.not.found"),
    COURIER_ALREADY_EXISTS(HttpStatus.CONFLICT, "courier.already.exists"),
    COURIER_PHONE_ALREADY_EXISTS(HttpStatus.CONFLICT, "courier.phone.already.exists"),
    COURIER_FORBIDDEN(HttpStatus.FORBIDDEN, "courier.forbidden"),

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "general.validation.error"),
    INVALID_REQUEST_FORMAT(HttpStatus.BAD_REQUEST, "general.invalid.request.format"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "general.internal.server.error"),
    CONFLICT(HttpStatus.CONFLICT, "general.conflict");

    private final HttpStatus status;
    private final String messageKey;
}
