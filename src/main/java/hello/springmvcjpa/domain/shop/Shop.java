package hello.springmvcjpa.domain.shop;

import hello.springmvcjpa.domain.Address;
import hello.springmvcjpa.domain.Pos;
import hello.springmvcjpa.domain.item.Item;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty
    private String shop;

    private String pos;
    /*@Embedded
    private Pos pos;*/



    /*@OneToMany(cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();*/

    //private List<Review> reviewList = new ArrayList<>(3);

    /*public Shop() {}*/

    public Shop(String shop, String pos) {
        this.shop = shop;
        this.pos = pos;
        //this.items = items;
    }

    public Shop change(Shop shop){
        this.shop=shop.getShop();
        this.pos=shop.getPos();
        return this;
    }

    /*public void editPos(Pos pos) {
        this.pos = pos;
    }*/

}
