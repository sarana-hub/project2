package hello.springmvcjpa.web.shop;

import hello.springmvcjpa.domain.item.Item;
import lombok.*;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopForm {
    private Long id;
    private String shopName;
    private String pos;

    /*@OneToMany
    private List<Item> items = new ArrayList<>();*/

}
