package hello.springmvcjpa.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomerRepository {
    private final EntityManager em;

    public List<Customer> findByLoginId(String loginId) {
        return em.createQuery("select m from Customer m where m.loginId=:loginId", Customer.class)
                .setParameter("loginId", loginId).getResultList();
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Customer findById(Long customerId) {
        return em.find(Customer.class, customerId);
    }
}
