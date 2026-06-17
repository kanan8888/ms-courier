package az.courierservice.dao.entity;

import az.courierservice.enums.CourierStatus;
import az.courierservice.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

import static az.courierservice.enums.CourierStatus.FREE;

@Entity
@Table(name = "couriers")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor @Builder @AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true) //bu ms-authda yaranan user id sidi, eslinde courierin id sini manual olaraq yazsam, bunu id kimi oture bilerem
    private UUID userId;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private VehicleType vehicleType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CourierStatus status = FREE;

    @Builder.Default
    @Column(nullable = false)
    private boolean isActive = true;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
