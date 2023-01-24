package hello.springmvcjpa.web.item;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {

    private Long id;

    private String itemName;

    @NumberFormat(pattern = "###,###")
    private Integer price;

    @NumberFormat(pattern = "###,###")
    private Integer stockQuantity;
}
