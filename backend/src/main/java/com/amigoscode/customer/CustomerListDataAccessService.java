package com.amigoscode.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDAO {

    // db  |  SHOULD NOT USE THIS, IT IS JUST TO TEST QUICKLY
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1L,
                "Alex",
                "alex@gmail.com",
                "password",
                21,
                Gender.MALE);
        Customer jamila = new Customer(
                2L,
                "Jamila",
                "jamila@gmail.com",
                "password",
                19,
                Gender.MALE);
        customers.add(alex);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers(SortCriteria sortBy, SortDirection sortDirection) {
        return null;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return null; // TODO: Implement this 😅
    }

    @Override
    public List<Customer> findCustomersByAge(Long age) {
        return null; // TODO: Implement this 😅
    }

    @Override
    public List<Customer> findCustomersByEmail(String email) {
        return null; // TODO: Implement this 😅
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customers.stream()
                .filter(c -> c.getUsername().equals(email))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customers.stream().filter(c -> c.getId().equals(customerId)).findFirst()
                .ifPresent(customers::remove); // equals to: (c -> customers.remove(c));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void updateCustomerProfileImageId(String profileImageId, Long customerId) {
        // TODO: Implement this 😅
    }
}