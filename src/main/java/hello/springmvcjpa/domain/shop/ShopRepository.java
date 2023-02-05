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

