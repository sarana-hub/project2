package hello.springmvcjpa.domain;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.item.Item;
import hello.springmvcjpa.domain.shop.Shop;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
//        initService.dbInit2();

    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {

            String loginId = "ww";
            String name1 = "점주다";
            String password = "0000";
            AccessRole role = AccessRole.OWNER;
            Address address = new Address("서울시 성북구 성북로5길", null,null, 07515);

            Shop shop=new Shop("메종드민서쓰", "경기 용인시 처인구 금학로 121");
            em.persist(shop);
            //Pos pos=new Pos("cityyy", "streeeet", 1201);
            //Shop shop=new Shop("cafe", pos);

            Customer customer=new Customer(loginId, password, name1, "01012345678", role, address);
            em.persist(customer);

            String loginId2 = "user";
            String name2 = "김유저";
            String password2 = "1111";
            AccessRole role2 = AccessRole.USER;
            Address address2 = new Address("서울시 강서구 공항대로 11", null, null, 01210);

            Customer customer2 = new Customer(loginId2, password2, name2, "01051049674", role2, address2);
            em.persist(customer2);

            String loginId3 = "ss";
            String name3 = "나유저";
            String password3 = "2222";
            AccessRole role3 = AccessRole.USER;
            Address address3 = new Address("서울시 양천구 신월로 375", null, null, 01375);

            Customer customer3 = new Customer(loginId3, password3, name3, "01047079674", role3, address3);
            em.persist(customer3);

            /*
            String name = "item_1";
            int price = 1000000;
            int stockQuantity = 100;
            String shop2="bhc";
            //Shop shop2=new Shop("bhc", "경기", null);

            Item item = new Item(name, price, stockQuantity, shop2, null);

            em.persist(item);*/
        }

        /*
        public void dbInit2() {

            String name = "item_2";
            int price = 20000;
            int stockQuantity = 10;
            String shop3="bing";
            //Shop shop3=new Shop("king", "인천", null);

            Item item = new Item(name, price, stockQuantity, shop3, null);

            em.persist(item);
        }*/
    }
}