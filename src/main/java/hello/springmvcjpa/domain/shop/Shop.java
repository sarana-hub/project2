package hello.springmvcjpa.domain.shop;

import hello.springmvcjpa.domain.item.Item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class Shop {

    @Id
    @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    private String shopName;
    private String pos;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    //private List<Review> reviewList = new ArrayList<>(3);

    /*public Shop() {}*/

    public Shop(String shopName, String pos, List<Item> items) {
        this.shopName = shopName;
        this.pos = pos;
        this.items = items;
    }

    public Shop change(Shop shop){
        this.shopName=shop.getShopName();
        this.pos=shop.getPos();
        this.items=shop.getItems();

        return this;
    }

}
