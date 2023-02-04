package hello.springmvcjpa.domain.shop;

import hello.springmvcjpa.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@RequiredArgsConstructor
@Repository
public class ShopRepository {

    private final EntityManager em;

    public Long save(Shop shop) {
        em.persist(shop);
        return shop.getId();
    }

    public Shop findById(Long shopId) {
        return em.find(Shop.class, shopId);
    }

    public List<Shop> findAll() {
        return em.createQuery("select s from Shop s", Shop.class)
                .getResultList();
    }
}

    /*private static Map<Long, Shop> store = new HashMap();
    private static long sequence = 0L;

    public ShopRepository() {
    }

    public static Shop newShop(Shop shop)
    {
        shop.setId(++sequence);
        store.put(shop.getId(), shop);
        return shop;
    }

    public Shop findById(Long id){
        return store.get(id);
    }

    public Collection<Shop> findAllShop(){
        return store.values();
    }

    //음식점 이름으로 음식점 정보 알기
    public Optional<Shop> findByStoreName(String shopName) {
        return store.values().stream().filter(m->m.getShopName().equals(shopName))
                .findFirst();
    }

    //음식점 이름으로 메뉴 정보 알기
    public List<Item> findItemByShopName(String shopName)
    {
        return findByStoreName(shopName).get().getItems();
    }

    public List<String> findItemNameById(Long id){
        List<String> itemNameList = new ArrayList<>();
        Shop shop = findById(id);
        List<Item> items = shop.getItems();
        for (Item item : items) {
            itemNameList.add(item.getItemName());
        }
        return itemNameList;
    }

    public void makeShopList() {
        List<Item> chickenList = new ArrayList<>();
        List<Item> pizzaList = new ArrayList<>();


        chickenList.add(new Item("friedChicken", 13000));
        chickenList.add(new Item("hotChicken", 18000));
        chickenList.add(new Item("coke", 3000));

        pizzaList.add(new Item("meatPizza", 16000));
        pizzaList.add(new Item("vegPizza", 19000));
        pizzaList.add(new Item("pasta", 60000));

        newShop(new Shop("chicken", "seoul", chickenList));
        newShop(new Shop("pizza", "yongin", pizzaList));
    }*/
