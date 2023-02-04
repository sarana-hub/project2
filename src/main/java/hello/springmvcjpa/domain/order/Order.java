package hello.springmvcjpa.domain.order;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.delivery.Delivery;
import hello.springmvcjpa.domain.delivery.DeliveryStatus;
import hello.springmvcjpa.domain.orderItem.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;*/

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    protected void setMember(Customer customer) {
        this.customer = customer;
    }

    protected void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    protected void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Customer customer, Delivery delivery, OrderItem orderItem) {
        Order order = new Order();
        order.setMember(customer);
        order.setDelivery(delivery);
        order.addOrderItem(orderItem);
        order.setOrderDate(LocalDateTime.now());
        order.orderStatus = OrderStatus.ORDER;

        return order;
    }

    public void cancel() {
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("배송이 완료된 주문은 취소할 수 없습니다.");
        }

        orderStatus = OrderStatus.CANCEL;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    public void comp() {
        if (orderStatus == OrderStatus.CANCEL) {
            throw new IllegalStateException("취소된 주문입니다");
        }
        delivery.setDeliveryStatus(DeliveryStatus.COMP);
    }
}