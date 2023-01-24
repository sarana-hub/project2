package hello.springmvcjpa.interceptor;

import hello.springmvcjpa.domain.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if ( session == null || session.getAttribute(SessionConst.LOGIN_CUSTOMER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/login?redirectURL="+request.getRequestURI());
            return false;
        }
        return true;
    }
}
