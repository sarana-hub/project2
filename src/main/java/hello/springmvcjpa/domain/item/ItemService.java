package hello.springmvcjpa.domain.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Transactional
    public Item update(Long itemId, Item item) {
        Item findItem = itemRepository.findById(itemId);
        return findItem.change(item);
    }

    @Transactional
    public Long save(Item item) {
        return itemRepository.save(item);
    }
}
