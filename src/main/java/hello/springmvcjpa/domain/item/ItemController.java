package hello.springmvcjpa.domain.item;

import hello.springmvcjpa.domain.file.FileStore;
//import hello.springmvcjpa.domain.shop.ShopService;
import hello.springmvcjpa.domain.shop.Shop;
import hello.springmvcjpa.domain.shop.ShopService;
import hello.springmvcjpa.web.owner.item.ItemForm;
import hello.springmvcjpa.web.owner.item.ItemSaveForm;
import hello.springmvcjpa.web.owner.item.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final ShopService shopService;

    private final FileStore fileStore;

    /**
     * 아이템 전1`체 조회 -상품 목록
     */
    @GetMapping("/items")
    public String items(Model model) {
        List<Item> items = itemService.findAll();
        List<ItemForm> forms = new ArrayList<>();
        for (Item item : items) {
            ItemForm form= new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity(),
                   item.getShop(), item.getImageFiles());
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
        ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity(),
                item.getShop(), item.getImageFiles());
        model.addAttribute("item", form);
        return "item/item";
    }

    /**
     * 점주 아이템 전체 조회
     */
    @GetMapping("/owner/items")
    public String adminItems(Model model) {

        List<Item> items = itemService.findAll();
        List<ItemForm> forms = new ArrayList<>();

        for (Item item : items) {
            ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity(),
                    item.getShop(), item.getImageFiles());
            forms.add(form);
        }

        model.addAttribute("items", forms);
        return "owner/itemList";
    }

    /*아이템 전체 조회
    @GetMapping()
    public String Items(Model model) {

        List<Item> items = itemService.findAll();
        List<ItemForm> forms = new ArrayList<>();

        for (Item item : items) {
            ItemForm form = new ItemForm(item.getId(), item.getItemName(), item.getPrice(), item.getStockQuantity(),
                    item.getShop(), item.getImageFiles());
            forms.add(form);
        }

        model.addAttribute("items", forms);
        return "index";
    }*/



    /**
     * 점주 아이템 등록 폼
     */
    @GetMapping("/owner/items/add")
    public String addForm(Model model) {
        List<Shop> shops=shopService.findAll();
        model.addAttribute("shops",shops);
        model.addAttribute("item", new ItemSaveForm());
        return "owner/addForm";
    }

    /**
     * 점주 아이템 등록
     */
    @PostMapping("/owner/items/add")
    public String add(@Validated @ModelAttribute("item") ItemSaveForm form,
                      BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        if (form.getPrice() != null && form.getStockQuantity() != null) {
            int resultPrice = form.getPrice() * form.getStockQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
//                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult.toString());
            return "owner/addForm";
        }

        Item item = new Item(form.getItemName(), form.getPrice(), form.getStockQuantity(),
                form.getShop(), storeImageFiles);

        Long itemId = itemService.save(item);
        redirectAttributes.addAttribute("itemId", itemId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/owner/items";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    /**
     * 점주 아이템 수정 폼
     */
    @GetMapping("/owner/items/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findById(itemId);
        model.addAttribute("item", item);
        return "owner/editItem";
    }

    /**
     * 점주 아이템 수정
     */
    @PostMapping("/owner/items/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId,
                       @Validated @ModelAttribute("item") ItemUpdateForm form,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        if (form.getPrice() != null && form.getStockQuantity() != null) {
            int resultPrice = form.getPrice() * form.getStockQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult.toString());
            return "owner/editItem";
        }

        /*Item item = new Item(form.getId(), form.getItemName(), form.getPrice(), form.getStockQuantity(),
                form.getShop(), form.getImageFiles());*/
        Item item = itemService.findById(itemId);
        item.setShop(form.getShop());
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());
        if(!storeImageFiles.isEmpty()) {
            item.setImageFiles(storeImageFiles);
        }

        itemService.update(itemId, item);
//        redirectAttributes.addAttribute("itemId", itemId);

        return "redirect:/owner/items";
    }
}
