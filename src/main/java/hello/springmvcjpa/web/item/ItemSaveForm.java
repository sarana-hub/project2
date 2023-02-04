package hello.springmvcjpa.web.item;

import hello.springmvcjpa.domain.shop.Shop;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemSaveForm {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer stockQuantity;

    //@NotBlank
    private String shop; //매장명

    private List<MultipartFile> imageFiles;
}
