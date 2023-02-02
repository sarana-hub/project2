package hello.springmvcjpa.domain.customer;

import hello.springmvcjpa.domain.AccessRole;
import hello.springmvcjpa.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Setter
//@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    //@NotEmpty
    private String loginId; //로그인 ID

    //@NotEmpty
    private String name; //사용자 이름
    //@NotEmpty
    private String password;
    //@NotEmpty
    //private String phone; //전화번호

    /*public void editPhone(String phone) {
        this.phone = phone;
    }*/

    @Enumerated
    private AccessRole role; //점주, 고객

    @Embedded
    private Address address;

    public Customer(String loginId, String password, String name, AccessRole role, Address address) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.role = role;
        this.address = address;
    }

    public void editAddress(Address address) {
        this.address = address;
    }
    /*@OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();*/
}
