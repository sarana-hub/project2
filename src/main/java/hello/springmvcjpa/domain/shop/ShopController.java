package hello.springmvcjpa.domain.shop;

import hello.springmvcjpa.domain.Address;
import hello.springmvcjpa.domain.Pos;
import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.customer.CustomerService;
import hello.springmvcjpa.domain.login.SessionConst;
import hello.springmvcjpa.web.shop.ShopForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShopController {
    private final ShopService shopService;
    private final CustomerService customerService;

    @GetMapping("/owner/shops/{shopId}")
    public String shop(@PathVariable("shopId") Long shopId, Model model){
    //public String shop(@PathVariable("shopId") Long shopId, @Valid @ModelAttribute("shop") ShopForm form, Model model){
        log.info("shopId = {}", shopId);
        Shop shop=shopService.findById(shopId);

        ShopForm form= new ShopForm(shop.getId(), shop.getShopName(), shop.getPos());
         //       shop.getPos().getCity(), shop.getPos().getStreet(), shop.getPos().getZipcode());
        //ShopForm form =new ShopForm();
        /*Pos pos = new Pos(form.getCity(), form.getStreet(), form.getZipcode());
        form=new ShopForm(shop.getId(), shop.getShopName(), pos);
        shop.setId(form.getId());
        shop.setShopName(form.getShopName());
        shop.setPos(pos);
        ShopForm form =new ShopForm();
        form.setId(shop.getId());
        form.setShopName(shop.getShopName());
        form.setCity(shop.getPos().getCity());
        form.setStreet(shop.getPos().getStreet());
        form.setZipcode(shop.getPos().getZipcode());*/

        model.addAttribute("shop",form);
        return "shop/shop";
    }

    /*owner detail +가게 전체 조회(가게목록)*/
    @GetMapping("/owner/shops")
    public String adminShops(HttpServletRequest request, Model model) {
    //public String adminShops(HttpServletRequest request, Pos pos, Model model) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        List<Shop> shops =shopService.findAll();
        List<ShopForm> forms = new ArrayList<>();

        for (Shop shop : shops) {
            //Pos pos = new Pos(form.getCity(), form.getStreet(), form.getZipcode());
            ShopForm form= new ShopForm(shop.getId(), shop.getShopName(), shop.getPos());
                    //shop.getPos().getCity(), shop.getPos().getStreet(), shop.getPos().getZipcode());
            /*ShopForm form =new ShopForm();
            form.setId(shop.getId());
            form.setShopName(shop.getShopName());
            form.setCity(form.getCity());
            form.setStreet(form.getStreet());
            form.setZipcode(form.getZipcode());*/
            /*ShopForm form =new ShopForm();
            form.setId(shop.getId());
            form.setShopName(shop.getShopName());*/
            /*form.setCity(shop.getPos().getCity());
            form.setStreet(shop.getPos().getStreet());
            form.setZipcode(shop.getPos().getZipcode());*/

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
            return "owner/addShop";
        }

        Shop shop = new Shop(form.getShopName(), form.getPos());
        //Pos pos=new Pos(form.getCity(), form.getStreet(), form.getZipcode()); //
        //Shop shop = new Shop(form.getShopName(), pos); //

        Long shopId= shopService.save(shop);

        redirectAttributes.addAttribute("shopId", shopId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/owner/shops";
    }


    //owner 회원 주소 수정
    @PostMapping("/owner/shops/address/editForm")
    public String editForm(Address address, Model model) {
        model.addAttribute("address", address);
        return "owner/infoEditForm";
    }
    @PostMapping("/owner/shops/address/edit")
    public String edit(Address address, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long customerId = (Long) session.getAttribute(SessionConst.LOGIN_CUSTOMER);

        customerService.addressEdit(customerId,address);

        return "redirect:/owner/shops";
    }


    /*
    @GetMapping("/loginHome")
    public String Home() {
        return "owner/home";
    }

    @GetMapping("/shops/add")
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
