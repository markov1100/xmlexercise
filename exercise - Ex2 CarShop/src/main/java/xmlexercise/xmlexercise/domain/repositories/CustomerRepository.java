package xmlexercise.xmlexercise.domain.repositories;


import xmlexercise.xmlexercise.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("select c from Customer as c   order by c.birthDate asc , c.youngDriver")
    Set<Customer> findAllAndSort();


     Set<Customer> findAllBySalesIsNotNull();

      }
