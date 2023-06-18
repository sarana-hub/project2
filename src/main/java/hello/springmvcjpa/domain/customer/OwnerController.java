package hello.springmvcjpa.domain.customer;

import hello.springmvcjpa.domain.Address;
import hello.springmvcjpa.domain.customer.CustomerService;
import hello.springmvcjpa.domain.login.SessionConst;
import hello.springmvcjpa.domain.shop.Shop;
import hello.springmvcjpa.domain.shop.ShopService;
import hello.springmvcjpa.web.shop.ShopForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class OwnerController {

    private final CustomerService customerService;

    /*@GetMapping("/owner")
    public String home() {
        return "/owner/home";
    }*/

    /*owner detail*/
    @GetMapping("/owner/info")
    public String owner(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "owner/info";
    }


    //owner 회원 주소 수정
    @PostMapping("/owner/address/editForm")
    public String editForm(Address address, Model model) {
        model.addAttribute("address", address);
        return "owner/infoEditForm";
    }
    @PostMapping("/owner/address/edit")
    public String edit(Address address, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        customerService.addressEdit(customerId,address);

        return "redirect:/owner/info";
    }
}
