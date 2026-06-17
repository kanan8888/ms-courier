package az.courierservice.dao.repository;

import az.courierservice.dao.entity.Courier;
import az.courierservice.enums.CourierStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourierRepository extends JpaRepository<Courier, UUID> {

    Optional<Courier> findByUserId(UUID userId);

    Optional<Courier> findByIdAndActiveTrue(UUID id);

    boolean existsByUserId(UUID userId);

    boolean existsByPhone(String phone);

    Page<Courier> findAllByActiveTrue(Pageable pageable);

    Page<Courier> findAllByStatusAndActiveTrue(CourierStatus status, Pageable pageable);

    List<Courier> findAllByStatusAndActiveTrue(CourierStatus status);

    @Query("""
            SELECT c FROM Courier c
            WHERE c.isActive = true
            AND (LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            OR LOWER(CONCAT(c.firstName, ' ', c.lastName))
                LIKE LOWER(CONCAT('%', :query, '%')))
            """)
    List<Courier> searchByName(@Param("query") String query);
}
