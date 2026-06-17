package az.courierservice.service.impl;

import az.courierservice.annotation.Log;
import az.courierservice.configuration.properties.EarningProperties;
import az.courierservice.dao.entity.Courier;
import az.courierservice.dao.repository.CourierDeliveryHistoryRepository;
import az.courierservice.dao.repository.CourierRepository;
import az.courierservice.dto.request.CreateCourierRequest;
import az.courierservice.dto.request.UpdateCourierRequest;
import az.courierservice.dto.request.UpdateCourierStatusRequest;
import az.courierservice.dto.response.CourierDeliveryHistoryResponse;
import az.courierservice.dto.response.CourierResponse;
import az.courierservice.enums.CourierStatus;
import az.courierservice.enums.Role;
import az.courierservice.event.OrderEvent;
import az.courierservice.exception.CourierException;
import az.courierservice.mapper.CourierMapper;
import az.courierservice.mapper.DeliveryHistoryMapper;
import az.courierservice.service.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static az.courierservice.enums.LogAction.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {

    private final CourierRepository courierRepository;
    private final CourierDeliveryHistoryRepository deliveryHistoryRepository;
    private final EarningProperties earningProperties;

    @Log(CreateCourier)
    public CourierResponse createCourier(CreateCourierRequest request) {
        if (courierRepository.existsByUserId(request.getUserId())) {
            throw CourierException.alreadyExists(request.getUserId());
        }

        if (courierRepository.existsByPhone(request.getPhone())) {
            throw CourierException.phoneAlreadyExists(request.getPhone());
        }

        var courier = courierRepository.save(
                CourierMapper.toCourier(request)
        );

        log.info("ActionLog.CreateCourier.info - courierId: {}", courier.getId());

        return CourierMapper.toCourierResponse(courier);
    }

    @Log(GetCourier)
    public CourierResponse getCourier(UUID courierId, UUID userId, Role role) {
        var courier = findById(courierId);

        if (Role.COURIER == role && !userId.equals(courier.getUserId())) {
            throw CourierException.forbidden();
        }

        return CourierMapper.toCourierResponse(courier);
    }

    @Log(GetCouriers)
    public Page<CourierResponse> getAllCouriers(Pageable pageable) {
        return courierRepository.findAll(pageable)
                .map(CourierMapper::toCourierResponse);
    }

    @Log(GetCouriers)
    public List<CourierResponse> getAvailableCouriers() {
        return courierRepository.findAllByStatusAndActiveTrue(CourierStatus.FREE)
                .stream()
                .map(CourierMapper::toCourierResponse)
                .toList();
    }

    @Log(UpdateCourier)
    public CourierResponse updateCourier(UUID courierId, UUID userId,
                                         UpdateCourierRequest request) {
        var courier = findById(courierId);

        if (!courier.getUserId().equals(userId)) {
            throw CourierException.forbidden();
        }

        CourierMapper.updateCourier(request, courier);
        courierRepository.save(courier);

        return CourierMapper.toCourierResponse(courier);
    }

    @Log(UpdateCourierStatus)
    public CourierResponse updateCourierStatus(UUID courierId,
                                               UpdateCourierStatusRequest request) {
        var courier = findById(courierId);
        courier.setStatus(request.getStatus());
        courierRepository.save(courier);

        return CourierMapper.toCourierResponse(courier);
    }

    @Log(DeleteCourier)
    public void deleteCourier(UUID courierId, UUID adminId) {
        var courier = findById(courierId);
        courier.setActive(false);
        courierRepository.save(courier);

    }

    @Log(GetCourierHistory)
    public Page<CourierDeliveryHistoryResponse> getDeliveryHistory(UUID courierId, UUID userId,
                                                                   Role role, Pageable pageable) {
        var courier = findById(courierId);

        if (role == Role.COURIER && !courier.getUserId().equals(userId)) {
            throw CourierException.forbidden();
        }

        return deliveryHistoryRepository.findByCourierId(courierId, pageable)
                .map(DeliveryHistoryMapper::toDeliveryHistoryResponse);
    }

    @Log(SearchCouriers)
    public List<CourierResponse> searchCouriersByName(String name) {
        return courierRepository.searchByName(name)
                .stream()
                .map(CourierMapper::toCourierResponse)
                .toList();
    }

    public void handleOrderAssigned(OrderEvent event) {
        courierRepository.findById(event.getCourierId()).ifPresent(courier -> {
            courier.setStatus(CourierStatus.BUSY);
            courierRepository.save(courier);
        });
    }

    public void handleOrderDelivered(OrderEvent event) {
        courierRepository.findById(event.getCourierId()).ifPresent(courier -> {
            courier.setStatus(CourierStatus.FREE);
            courierRepository.save(courier);

            var earning = event.getDeliveryFee()
                    .multiply(earningProperties.getEarningPercentage())
                    .setScale(2, RoundingMode.HALF_UP);

            var history = DeliveryHistoryMapper.toDeliveryHistory(event, courier, earning);

            deliveryHistoryRepository.save(history);
        });
    }

    public void handleOrderCancelled(OrderEvent event) {
        if (event.getCourierId() == null) return;

        courierRepository.findById(event.getCourierId()).ifPresent(courier -> {
            courier.setStatus(CourierStatus.FREE);
            courierRepository.save(courier);
        });
    }

    private Courier findById(UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(() -> CourierException.notFound(courierId));
    }
}
