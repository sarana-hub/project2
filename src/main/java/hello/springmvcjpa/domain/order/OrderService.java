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
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.StorageType;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public void createOrder(Long itemId, Long customerId, int count) throws IOException {
        Customer customer=customerRepository.findById(customerId);
        Item item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery(DeliveryStatus.READY, customer.getPhone());

        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        Order order = Order.createOrder(customer, delivery, orderItem);

        orderRepository.save(order);
        for(int i=0; i<count; i++){
            sendSMS(customer.getPhone(), item.getItemName());
        }

    }

    @Transactional
    public void createOrder2(Long itemId, Long customerId, int count, String phone, String msg) throws IOException {
        Customer customer=customerRepository.findById(customerId);
        Item item = itemRepository.findById(itemId);

        Delivery delivery = new Delivery(DeliveryStatus.READY, customer.getPhone());

        OrderItem orderItem = OrderItem.createOrderItem(item, count, item.getPrice());

        Order order = Order.createOrder(customer, delivery, orderItem);

        orderRepository.save(order);

        for(int i=0; i<count; i++){
            giftSMS(phone, msg, item.getItemName());
        }
    }

    //쿠폰번호 생성
    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 12; i++) { //쿠폰번호 12자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }


    //주문시
    private void sendSMS(String hp, String item) throws IOException {
        final DefaultMessageService messageService =
                NurigoApp.INSTANCE.initialize("NCSYJZG", "GHZGZHMY6VLESL1PR","https://api.coolsms.co.kr");

        ClassPathResource resource = new ClassPathResource("static/cti.jpg");
        File file = resource.getFile();
        String imageId = messageService.uploadFile(file, StorageType.MMS, "https://example.com");

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01051049674");
        message.setTo(hp);
        message.setText(item+"\n쿠폰번호: "+createKey());
        //message.setImageId(imageId);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
    }
    //선물시
    private void giftSMS(String hp, String text, String item) throws IOException {
        final DefaultMessageService messageService =
                NurigoApp.INSTANCE.initialize("NCSYJZ", "GHZGZHMY6VLESL","https://api.coolsms.co.kr");

        ClassPathResource resource = new ClassPathResource("static/sample.jpg");
        File file = resource.getFile();
        String imageId = messageService.uploadFile(file, StorageType.MMS, "https://example.com");

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("01051049674");
        message.setTo(hp);
        message.setText(item+"\n쿠폰번호: "+createKey()+"\n"+text);
        //message.setImageId(imageId);

        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
    }


    /** 주문 내역 검색 */
    public List<Order> findOrders(OrderSearch orderSearch) {

        return orderRepository.findAllByString(orderSearch);
    }

    public List<Order> findOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order findOrder = orderRepository.findById(orderId);
        findOrder.cancel();
    }

    @Transactional
    public void comp(Long orderId) {
        Order findOrder = orderRepository.findById(orderId);
        findOrder.comp();
    }

    public List<Order> findPersonalOrders(Long customerId) {
        List<Order> orders = orderRepository.findOrderByMemberId(customerId);
        return orders;
    }


    /*public List<Order> findShopOrders(Long shopId) {
        List<Order> orders = orderRepository.findOrderByShop(shopId);
        return orders;
    }*/
}
