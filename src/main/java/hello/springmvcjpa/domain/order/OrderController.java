package hello.springmvcjpa.domain.order;

import hello.springmvcjpa.domain.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/add")
    public String add(@RequestParam("itemId") Long itemId, @RequestParam("count") int count, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        orderService.createOrder(itemId, customerId, count);

        return "redirect:/items";
    }
    @PostMapping("/orders/add2")
    public String add2(@RequestParam("itemId") Long itemId, @RequestParam("cnt") int cnt,
                       @RequestParam("phone") String phone, @RequestParam("msg") String msg, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        orderService.createOrder2(itemId, customerId, cnt, phone, msg);

        return "redirect:/items";
    }

    @PostMapping("/owner/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/owner/orders";
    }

    @PostMapping("/owner/orders/{orderId}/comp")
    public String comp(@PathVariable("orderId") Long orderId) {
        orderService.comp(orderId);
        return "redirect:/owner/orders";
    }

    /*@GetMapping("/owner/orders")
    public String orderList(Model model) {
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "owner/orderList";
    }*/
    @GetMapping("/owner/orders")  //주문 목록 검색
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "owner/orderList";
    }
    /*@GetMapping("/owner/orders")
    public String orderList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long shopId= (Long) session.getAttribute(SessionConst.LOGIN_SHOP);
        List<Order> orders =orderService.findShopOrders(shopId);
        model.addAttribute("orders", orders);
        return "owner/orderList";
    }*/


    @GetMapping("/orders")
    public String orders(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long customerId =(Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        List<Order> orders = orderService.findPersonalOrders(customerId);

        model.addAttribute("orders", orders);

        return "order/order";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancel2(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/orders";
    }


}
