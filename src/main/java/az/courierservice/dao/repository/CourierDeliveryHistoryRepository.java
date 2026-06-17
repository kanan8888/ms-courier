package az.courierservice.dao.repository;

import az.courierservice.dao.entity.CourierDeliveryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourierDeliveryHistoryRepository extends JpaRepository<CourierDeliveryHistory, UUID> {
    Page<CourierDeliveryHistory> findByCourierId(UUID courierId, Pageable pageable);
}
