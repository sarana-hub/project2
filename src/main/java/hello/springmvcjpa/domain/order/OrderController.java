package hello.springmvcjpa.domain.order;

import hello.springmvcjpa.domain.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/add")
    public String add(@RequestParam("itemId") Long itemId, @RequestParam("count") int count, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        orderService.createOrder(itemId, customerId, count);

        return "redirect:/items";
    }

    @GetMapping("/owner/orders")
    public String orderList(Model model) {
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "owner/orderList";
    }

    @PostMapping("/owner/orders/{orderId}/cancel")
    public String cancel(@PathVariable("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/owner/orders";
    }

    @GetMapping("/orders")
    public String orders(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Long customerId =(Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        List<Order> orders = orderService.findPersonalOrders(customerId);

        model.addAttribute("orders", orders);

        return "order/order";
    }
}
