package hello.springmvcjpa.domain;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.customer.CustomerRepository;
import hello.springmvcjpa.domain.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {

    private final CustomerRepository customerRepository;

    //@GetMapping("/")
    public String home() {
        return "index.html";
    }

    @GetMapping("/")
    public String loginHome(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);

        if (session == null) {
            return "index";
        }

        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        if (customerId == null) {
            return "index";
        }

        Customer customer=customerRepository.findById(customerId);

        model.addAttribute("customer", customer);
        return "login/loginHome";
    }
}

