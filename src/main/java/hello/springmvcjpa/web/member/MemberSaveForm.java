package hello.springmvcjpa.web.member;

import hello.springmvcjpa.domain.AccessRole;
import hello.springmvcjpa.domain.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    private String phone;

    private AccessRole role;

    /*Shop*/
    private String shop;
    private String pos;
    private List<Item> items = new ArrayList<>();

    /*Address*/
    @NotEmpty
    private String city;
    //@NotEmpty
    private String detailAddress;
    //@NotEmpty
    private String street;
    @NotNull
    private Integer zipcode;
}
