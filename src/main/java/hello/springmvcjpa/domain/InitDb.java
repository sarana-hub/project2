package hello.springmvcjpa.domain;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 2개
 * userA
 *  JPA1 BOOK
 *  JPA2 BOOK
 * userB
 *  SPRING1 BOOK
 *  SPRING2 BOOK
 */
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

            String loginId = "admin";
            String name1 = "관리자";
            String password = "admin!";
            AccessRole role = AccessRole.ADMIN;
            Address address = new Address("city", "street", 1111);

            Customer customer=new Customer(loginId, password, name1, role, address);
            em.persist(customer);

            String loginId2 = "user";
            String name2 = "유저";
            String password2 = "1234";
            AccessRole role2 = AccessRole.USER;
            Address address2 = new Address("city2", "street2", 2222);

            Customer customer2 = new Customer(loginId2, password2, name2,  role2, address2);
            em.persist(customer2);

            String name = "test_init_item_1";
            int price = 1000000;
            int stockQuantity = 100;
            String shop="cafe12";

            Item item = new Item(name, price, stockQuantity, shop, null);

            em.persist(item);
        }
        public void dbInit2() {

            String name = "test_init_item_2";
            int price = 20000;
            int stockQuantity = 10;
            String shop="king";

            Item item = new Item(name, price, stockQuantity, shop, null);

            em.persist(item);
        }
    }
}