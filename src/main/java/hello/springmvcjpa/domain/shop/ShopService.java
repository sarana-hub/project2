package hello.springmvcjpa.domain.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public List<Shop> findAll() {
        return shopRepository.findAll();
    }

    public Shop findById(Long shopId) {
        return shopRepository.findById(shopId);
    }

    @Transactional
    public Shop update(Long shopId, Shop shop) {
        Shop findShop = shopRepository.findById(shopId);
        return findShop.change(shop);
    }

    @Transactional
    public Long save(Shop shop) {
        return shopRepository.save(shop);
    }
}
