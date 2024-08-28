package com.amigoscode.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {

    List<Customer> selectAllCustomers(SortCriteria sortBy, SortDirection sortDirection);

    List<Customer> selectAllCustomers();

    void insertCustomer(Customer customer);

    void updateCustomer(Customer update);

    void deleteCustomerById(Long customerId);

    Optional<Customer> selectCustomerById(Long customerId);

    List<Customer> findCustomersByName(String name);

    List<Customer> findCustomersByAge(Long age);

    List<Customer> findCustomersByEmail(String email);

    Optional<Customer> selectUserByEmail(String email);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerWithId(Long customerId);

    void updateCustomerProfileImageId(String profileImageId, Long customerId);
}
