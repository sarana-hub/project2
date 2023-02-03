package hello.springmvcjpa.domain.login;

import hello.springmvcjpa.domain.AccessRole;
import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.web.login.LoginForm;
import hello.springmvcjpa.web.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("/login")
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final LoginService loginService;

    /*@GetMapping
    public String loginForm(Model model) {
        model.addAttribute("customer", new MemberSaveForm());
        return "login/loginForm";
    }*/
    @GetMapping
    public String loginForm(Model model) {

        model.addAttribute("member", new MemberSaveForm());
        return "login/loginForm";
    }



    @PostMapping
    public String login(@Validated @ModelAttribute("customer") LoginForm form
            , BindingResult bindingResult
            , HttpServletRequest request
            , HttpServletResponse response
            ,@RequestParam(defaultValue = "/") String redirectURL) {

        // 로그인 서비스
        //Member member = loginService.login(form.getLoginId(), form.getPassword());
        Customer customer=loginService.login(form.getLoginId(), form.getPassword());


        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "login/loginForm";
        }

        if (customer == null) {
            bindingResult.reject("error.notMatched", "아이디 혹은 비밀번호가 틀립니다");
            return "login/loginForm";
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_CUSTOMER,customer.getId());
        session.setAttribute("role",customer.getRole());
        session.setMaxInactiveInterval(600);// 10분

        log.info("redirectURL = {}", redirectURL);

        if(customer.getRole() == AccessRole.OWNER){
            return "redirect:/owner";
        }

         return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();

        return "redirect:/";
    }
}
