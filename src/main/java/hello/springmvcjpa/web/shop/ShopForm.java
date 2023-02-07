package hello.springmvcjpa.web.shop;

import hello.springmvcjpa.domain.Pos;
import hello.springmvcjpa.domain.item.Item;
import lombok.*;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

/*@Data
@NoArgsConstructor
@AllArgsConstructor*/
public class ShopForm {
    private Long id;
    private String shopName;

    private String pos;
    /*Pos*/
    /*@NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotNull
    private Integer zipcode;*/

}
