package hello.springmvcjpa.domain.shop;

import hello.springmvcjpa.web.shop.ShopForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShopController {
    private final ShopService shopService;

/*@GetMapping("/loginHome")
    public String Home() {
        return "owner/home";
    }*/

    @GetMapping("/owner/shops/{shopId}")
    public String shop(@PathVariable("shopId") Long shopId, Model model){
        log.info("shopId = {}", shopId);
        Shop shop=shopService.findById(shopId);
        ShopForm form=new ShopForm(shop.getId(), shop.getShopName(), shop.getPos(), shop.getItems());
        model.addAttribute("shop",form);
        return "shop/shop";
    }

    /**가게 전체 조회(가게목록)*/
    @GetMapping("/owner/shops")
    public String adminShops(Model model) {

        List<Shop> shops =shopService.findAll();
        List<ShopForm> forms = new ArrayList<>();

        for (Shop shop : shops) {
            ShopForm form = new ShopForm(shop.getId(), shop.getShopName(), shop.getPos(), shop.getItems());
            forms.add(form);
        }

        model.addAttribute("shops", forms);
        return "owner/shopList";
    }

    @GetMapping("/owner/shops/add")
    public String getShop(Model model){
        model.addAttribute("shop", new ShopForm());
        return "owner/addShop";
    }

    @PostMapping("/owner/shops/add")
    public String add(@Validated @ModelAttribute("shop") ShopForm form, BindingResult bindingResult,
                      RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult.toString());
            return "owner/addForm";
        }

        Shop shop = new Shop(form.getShopName(), form.getPos(), form.getItems());

        Long shopId= shopService.save(shop);

        redirectAttributes.addAttribute("shopId", shopId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/owner/shops";
    }

   /* @GetMapping("/shops/add")
    public String getShop(Model model){
        model.addAttribute("shop", new Shop());
        return "owner/addShop";
    }

    @PostMapping("/shops/add")
    public String addShop(@ModelAttribute Shop shop){
        Shop addedShop = ShopRepository.newShop(shop);
        if (addedShop == null) {
            throw new RuntimeException("가게 추가 오류 발생");
        }
        return "owner/addShopCheck";
    }*/
}
