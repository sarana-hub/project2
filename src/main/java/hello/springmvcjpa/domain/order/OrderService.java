package hello.springmvcjpa.domain.order;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.customer.CustomerRepository;
import hello.springmvcjpa.domain.delivery.Delivery;
import hello.springmvcjpa.domain.delivery.DeliveryStatus;
import hello.springmvcjpa.domain.item.Item;
import hello.springmvcjpa.domain.item.ItemRepository;
import hello.springmvcjpa.domain.orderItem.OrderItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void createOrder(Long itemId, Long customerId, int count) {
        Customer customer=customerRepository.findById(customerId);
        Item item = itemRepository.findById(itemId);

        //Delivery delivery = new Delivery(DeliveryStatus.READY, customer.getAddress());
        Delivery delivery = new Delivery(DeliveryStatus.READY, customer.getPhone());

        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        Order order = Order.createOrder(customer, delivery, orderItem);

        orderRepository.save(order);
    }

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order findOrder = orderRepository.findById(orderId);
        findOrder.cancel();
    }

    public List<Order> findPersonalOrders(Long customerId) {
        List<Order> orders = orderRepository.findOrderByMemberId(customerId);
        return orders;
    }
}
