package hello.springmvcjpa.domain.login;

import hello.springmvcjpa.domain.customer.Customer;
import hello.springmvcjpa.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {

    private final CustomerRepository customerRepository;

    /**
     * 로그인
     */
    /*public Customer login(String loginId, String password) {
        return customerRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }*/
    public Customer login(String loginId, String password) {

        List<Customer> customers = customerRepository.findByLoginId(loginId);
        if (customers == null) {
            return null;
        } else {
            for (Customer customer : customers) {
                if (customer.getPassword().equals(password)) {
                    return customer;
                }
            }
        }
        return null;
    }
}
