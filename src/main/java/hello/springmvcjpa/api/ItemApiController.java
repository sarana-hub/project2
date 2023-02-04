package hello.springmvcjpa.api;


import hello.springmvcjpa.domain.file.FileStore;
import hello.springmvcjpa.domain.item.Item;
import hello.springmvcjpa.domain.item.ItemService;
import hello.springmvcjpa.domain.item.UploadFile;
import hello.springmvcjpa.web.item.ItemSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/api/items")
@RequiredArgsConstructor
@RestController
public class ItemApiController {

    private final ItemService itemService;
    private final FileStore fileStore;

    /**
     * 상품 등록 API
     */
    @PostMapping("/add")
    public Object add(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult) throws IOException {

        log.info("API 컨트롤러 호출");

        //검증 실패 시
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("성공 로직 실행");

        List<UploadFile> storeImageFiles = fileStore.storeFiles(form.getImageFiles());

        Item item = new Item(form.getItemName(), form.getPrice(), form.getStockQuantity(), form.getShop(), storeImageFiles);
        Long saveResult = itemService.save(item);
        SuccessForm successForm = new SuccessForm(item);

        return successForm;
    }

}
