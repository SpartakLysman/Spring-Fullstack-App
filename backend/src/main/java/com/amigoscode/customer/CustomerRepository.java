package com.amigoscode.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);

    boolean existsCustomerById(Long id);

    boolean existsCustomerByEmail(String email);

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByEmailContainingIgnoreCase(String email);

    Optional<Customer> findCustomerByEmail(String email);

    List<Customer> findByAge(Integer age);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Customer c SET c.profileImageId = ?1 WHERE c.id = ?2")
    int updateProfileImageId(String profileImageId, Long customerId);
}
