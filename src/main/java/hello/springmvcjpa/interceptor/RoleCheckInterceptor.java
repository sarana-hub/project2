package hello.springmvcjpa.interceptor;

import hello.springmvcjpa.domain.AccessRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("role") != AccessRole.OWNER) {
            log.info("권한 없는 사용자 요청");
            response.sendError(401);
            return false;
        }
        return true;
    }
}
