package hello.springmvcjpa.domain.item;

import hello.springmvcjpa.web.owner.item.ItemForm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ItemForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemForm item = (ItemForm) target;

        BindingResult bindingResult = (BindingResult) errors;

        if (!StringUtils.hasText(item.getItemName())) {
//            bindingResult.rejectValue("itemName", "require");
            bindingResult.addError(new FieldError("item","itemName", item.getItemName(), false, new String[]{"require"},null,null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
            bindingResult.addError(new FieldError("item","price",item.getPrice(), false, new String[]{"range"}, new Object[]{1000,1000000},null));
        }

        if (item.getStockQuantity() == null || item.getStockQuantity() > 9999) {
//            bindingResult.rejectValue("stockQuantity", "max", new Object[]{9999}, null);
            bindingResult.addError(new FieldError("item","stockQuantity", item.getStockQuantity(), false, new String[]{"max"},new Object[]{9999},null));
        }

        if (item.getPrice() != null && item.getStockQuantity() != null) {
            int resultPrice = item.getPrice() * item.getStockQuantity();
            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice},null));
            }
        }

    }
}
