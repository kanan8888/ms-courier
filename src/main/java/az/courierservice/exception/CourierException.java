package az.courierservice.exception;

import az.courierservice.enums.ErrorCode;
import az.courierservice.exception.base.AppException;

import java.util.UUID;

public class CourierException extends AppException {

    public CourierException(ErrorCode errorCode, String logMessage) {
        super(errorCode, logMessage);
    }

    public static CourierException notFound(UUID courierId) {
        return new CourierException(ErrorCode.COURIER_NOT_FOUND,
                "Courier not found with id: " + courierId);
    }

    public static CourierException notFoundByUserId(UUID userId) {
        return new CourierException(
                ErrorCode.COURIER_NOT_FOUND,
                "Courier not found with userId: " + userId
        );
    }

    public static CourierException alreadyExists(UUID userId) {
        return new CourierException(ErrorCode.COURIER_ALREADY_EXISTS,
                "Courier already exists for userId: " + userId);
    }

    public static CourierException phoneAlreadyExists(String phone) {
        return new CourierException(ErrorCode.COURIER_PHONE_ALREADY_EXISTS,
                "Phone already in use: " + phone);
    }

    public static CourierException forbidden() {
        return new CourierException(ErrorCode.COURIER_FORBIDDEN,
                "Access to this resource is forbidden");
    }
}
