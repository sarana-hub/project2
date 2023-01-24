package hello.springmvcjpa.api;

import hello.springmvcjpa.domain.item.Item;
import lombok.Data;

@Data
public class SuccessForm {
    private boolean success = true;
    private Item item;

    public SuccessForm(Item item) {
        this.item = item;
    }
}
