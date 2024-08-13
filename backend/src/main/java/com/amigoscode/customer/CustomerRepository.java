package com.amigoscode.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsCustomerById(Long id);

    boolean existsCustomerByEmail(String email);
}
