package hello.springmvcjpa.domain.item;

import hello.springmvcjpa.web.item.ItemForm;
import hello.springmvcjpa.web.item.ItemSaveForm;
import hello.springmvcjpa.web.item.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    /**
     * 아이템 전체 조회
     */
    @GetMapping("/items")
    public String items(Model model) {
        List<Item> items = itemService.findAll();
        List<ItemForm> forms = new ArrayList<>();
        for (Item item : items) {
            ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity());
            forms.add(form);
        }
        model.addAttribute("items", forms);
        return "item/itemList";
    }


    /**
     * 단일 아이템 조회
     */
    @GetMapping("/items/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        log.info("itemId = {}", itemId);
        Item item = itemService.findById(itemId);
        ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity());
        model.addAttribute("item", form);
        return "item/item";
    }

    /**
     * 관리자 아이템 전체 조회
     */
    @GetMapping("/admin/items")
    public String adminItems(Model model) {
        List<Item> items = itemService.findAll();
        List<ItemForm> forms = new ArrayList<>();
        for (Item item : items) {
            ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity());
            forms.add(form);
        }
        model.addAttribute("items", forms);
        return "admin/itemList";
    }



    /**
     * 관리자 아이템 등록 폼
     */
    @GetMapping("/admin/items/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemSaveForm());
        return "admin/addForm";
    }

    /**
     * 관리자 아이템 등록
     */
    @PostMapping("/admin/items/add")
    public String add(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (form.getPrice() != null && form.getStockQuantity() != null) {
            int resultPrice = form.getPrice() * form.getStockQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
//                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult.toString());
            return "admin/addForm";
        }

        Item item = new Item(form.getItemName(), form.getPrice(), form.getStockQuantity());

        Long itemId = itemService.save(item);
        redirectAttributes.addAttribute("itemId", itemId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/admin/items";
    }

    /**
     * 관리자 아이템 수정 폼
     */
    @GetMapping("/admin/items/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "admin/editItem";
    }

    /**
     * 관리자 아이템 수정
     */
    @PostMapping("/admin/items/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId,
                       @Validated @ModelAttribute("item") ItemUpdateForm form,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (form.getPrice() != null && form.getStockQuantity() != null) {
            int resultPrice = form.getPrice() * form.getStockQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult.toString());
            return "admin/editItem";
        }

        Item item = new Item(form.getId(), form.getItemName(), form.getPrice(), form.getStockQuantity());

        itemService.update(itemId, item);
//        redirectAttributes.addAttribute("itemId", itemId);

        return "redirect:/admin/items";
    }
}
