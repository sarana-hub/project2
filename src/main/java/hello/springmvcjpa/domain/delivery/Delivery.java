package hello.springmvcjpa.domain.delivery;

import hello.springmvcjpa.domain.Address;
import hello.springmvcjpa.domain.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    /*@Embedded
    private Address address;*/
    @Embedded
    private String phone;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public Delivery(DeliveryStatus deliveryStatus, String phone) {
        this.deliveryStatus = deliveryStatus;
        this.phone = phone;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}