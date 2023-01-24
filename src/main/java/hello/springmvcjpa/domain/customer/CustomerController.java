package hello.springmvcjpa.domain.customer;

import hello.springmvcjpa.domain.AccessRole;
import hello.springmvcjpa.domain.Address;
import hello.springmvcjpa.domain.login.SessionConst;
import hello.springmvcjpa.web.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class CustomerController {

    private final CustomerService customerService;

    //@GetMapping("/customers/add")
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("member", new MemberSaveForm());
        return "member/addForm";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("customer") MemberSaveForm form, BindingResult bindingResult) {
        List<Customer> sameIdMember = customerService.findByLoginId(form.getLoginId());

        if (!sameIdMember.isEmpty()) {
            bindingResult.rejectValue("loginId","error.already","이미 존재하는 아이디 입니다.");
        }
        if (bindingResult.hasErrors()) {
            return "member/addForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Customer customer = new Customer(form.getLoginId(),form.getPassword(), form.getName(), AccessRole.USER, address);
        customerService.save(customer);

        return "redirect:/";
    }

    //@PostMapping("/customers/add")
    /*@PostMapping("/add")
    public String save(@Valid @ModelAttribute Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/addForm";
        }
        customerRepository.save(customer);
        return "redirect:/";
    }*/

    @GetMapping("/info")
    public String info(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "member/info";
    }

    @PostMapping("/address/editForm")
    public String editForm(Address address, Model model) {
        model.addAttribute("address", address);
        return "member/infoEditForm";
    }

    @PostMapping("/address/edit")
    public String edit(Address address, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        customerService.addressEdit(customerId,address);

        return "redirect:/members/info";
    }
}
