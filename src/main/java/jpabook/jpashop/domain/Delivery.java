package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // ordinal을 쓰면 이후 상태 추가가 필요할 때, 추가되지 않기 때문에 반드시 string을 쓸 것 (추가하면 장애 남)
    private DeliveryStatus status; // READY, COMP

}
