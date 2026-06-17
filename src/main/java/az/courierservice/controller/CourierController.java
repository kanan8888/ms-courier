package az.courierservice.controller;

import az.courierservice.dto.request.CreateCourierRequest;
import az.courierservice.dto.request.UpdateCourierRequest;
import az.courierservice.dto.request.UpdateCourierStatusRequest;
import az.courierservice.dto.response.CourierDeliveryHistoryResponse;
import az.courierservice.dto.response.CourierResponse;
import az.courierservice.enums.Role;
import az.courierservice.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(CREATED)
    public CourierResponse createCourier(
            @Valid @RequestBody CreateCourierRequest request) {
        return courierService.createCourier(request);
    }

    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @GetMapping("/{courierId}")
    public CourierResponse getCourier(
            @PathVariable UUID courierId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Role") Role role) {
        return courierService.getCourier(courierId, userId, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<CourierResponse> getAllCouriers(Pageable pageable) {
        return courierService.getAllCouriers(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/available")
    public List<CourierResponse> getAvailableCouriers() {
        return courierService.getAvailableCouriers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{courierId}/status")
    public CourierResponse updateCourierStatus(
            @PathVariable UUID courierId,
            @Valid @RequestBody UpdateCourierStatusRequest request) {
        return courierService.updateCourierStatus(courierId, request);
    }

    @PreAuthorize("hasRole('COURIER')")
    @PutMapping("/{courierId}")
    public CourierResponse updateCourier(
            @PathVariable UUID courierId,
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody UpdateCourierRequest request) {
        return courierService.updateCourier(courierId, userId, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{courierId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCourier(
            @PathVariable UUID courierId,
            @RequestHeader("X-User-Id") UUID adminId) {
        courierService.deleteCourier(courierId, adminId);
    }

    @PreAuthorize("hasAnyRole('COURIER', 'ADMIN')")
    @GetMapping("/{courierId}/history")
    public Page<CourierDeliveryHistoryResponse> getDeliveryHistory(
            @PathVariable UUID courierId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestHeader("X-User-Role") Role role,
            Pageable pageable) {
        return courierService.getDeliveryHistory(courierId, userId, role, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public List<CourierResponse> searchCouriers(
            @RequestParam String name) {
        return courierService.searchCouriersByName(name);
    }
}
