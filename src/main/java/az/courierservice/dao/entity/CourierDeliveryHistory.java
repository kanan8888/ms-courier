package az.courierservice.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "courier_delivery_history")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourierDeliveryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal deliveryFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal courierEarning;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal distanceKm;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant deliveredAt;
}
