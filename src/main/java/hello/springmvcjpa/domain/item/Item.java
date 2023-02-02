package hello.springmvcjpa.domain.item;

import hello.springmvcjpa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private Integer price;

    private Integer stockQuantity; //재고수량

    private String shop; //매장명
    /*@Embedded
    private Shop shop; Address처럼*/

    @OneToMany(cascade = CascadeType.ALL)
    private List<UploadFile> imageFiles=new ArrayList<>();
    //private List<UploadFile> imageFiles;

    public Item(String itemName, Integer price, Integer stockQuantity, String shop, List<UploadFile> imageFiles) {
        this.itemName = itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.shop = shop;
        this.imageFiles = imageFiles;
    }

    public Item change(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.shop=item.getShop();
        this.imageFiles=item.getImageFiles();

        return this;
    }

    public void removeStock(int count) {
        int restStock = this.stockQuantity - count;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
