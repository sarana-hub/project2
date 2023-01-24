package hello.springmvcjpa.domain.customer;

import hello.springmvcjpa.domain.Address;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findByLoginId(String loginId) {
        return customerRepository.findByLoginId(loginId);
    }

    @Transactional
    public void save(Customer member) {
        customerRepository.save(member);
    }

    public Customer findById(Long memberId) {
        return customerRepository.findById(memberId);
    }

    @Transactional
    public void addressEdit(Long customerId, Address address) {
        Customer customer = customerRepository.findById(customerId);
        customer.editAddress(address);
    }
}
