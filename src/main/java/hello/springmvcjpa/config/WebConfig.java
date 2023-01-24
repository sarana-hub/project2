package hello.springmvcjpa.config;

import hello.springmvcjpa.interceptor.LogInterceptor;
import hello.springmvcjpa.interceptor.LoginCheckInterceptor;
import hello.springmvcjpa.interceptor.RoleCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/items", "/members/add", "/logout", "/login", "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new RoleCheckInterceptor())
                .order(3)
                .addPathPatterns("/admin/**");
    }
}
