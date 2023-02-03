package hello.springmvcjpa.web.member;

import hello.springmvcjpa.domain.AccessRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

    /*Address*/
    @NotEmpty
    private String city;
    @NotEmpty
    private String street;
    @NotNull
    private Integer zipcode;
}
