package hello.springmvcjpa.web.owner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/owner")
@Controller
public class OwnerController {

    @GetMapping
    public String home() {
        return "/owner/home";
    }

}
