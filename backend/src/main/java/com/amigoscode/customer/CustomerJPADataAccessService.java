package com.amigoscode.customer;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDAO {

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers(SortCriteria sortBy, SortDirection sortDirection) {
        return null;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        Page<Customer> page = customerRepository.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findCustomersByAge(Long age) {
        return null;
    }

    @Override
    public List<Customer> findCustomersByEmail(String email) {
        return null;
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        return customerRepository.existsCustomerById(id);
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public void updateCustomer(Customer update) {
        customerRepository.save(update);
    }

    @Override
    public void updateCustomerProfileImageId(String profileImageId, Long customerId) {
        customerRepository.updateProfileImageId(profileImageId, customerId);
    }
}
