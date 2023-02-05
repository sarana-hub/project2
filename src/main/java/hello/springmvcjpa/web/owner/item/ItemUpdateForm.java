package hello.springmvcjpa.web.owner.item;

import hello.springmvcjpa.domain.shop.Shop;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    private Integer stockQuantity;

    //@NotBlank
    private String shop; //매장명

    private List<MultipartFile> imageFiles;
}
