package com.amigoscode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerJPADataAccessServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerJPADataAccessService underTest;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        Page<Customer> page = mock(Page.class);
        List<Customer> customers = List.of(new Customer());
        when(page.getContent()).thenReturn(customers);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);
        // When
        List<Customer> expected = underTest.selectAllCustomers();

        // Then
        assertThat(expected).isEqualTo(customers);
        ArgumentCaptor<Pageable> pageArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(customerRepository).findAll(pageArgumentCaptor.capture());
        assertThat(pageArgumentCaptor.getValue()).isEqualTo(Pageable.ofSize(1000));
    }

    @Test
    void selectCustomerById() {
        long id = 1;

        underTest.selectCustomerById(id);

        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer customer =
                new Customer(1L, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);

        underTest.insertCustomer(customer);

        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        String email = "alex@gmail.com";

        underTest.existsCustomerWithEmail(email);

        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        long id = 1L;

        underTest.existsCustomerWithId(id);

        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        long id = 1L;

        underTest.deleteCustomerById(id);

        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        Customer customer =
                new Customer(1L, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);

        underTest.updateCustomer(customer);

        verify(customerRepository).save(customer);
    }

    @Test
    void canUpdateProfileImage() {
        String profileImageId = "2222";
        long customerId = 1;

        underTest.updateCustomerProfileImageId(profileImageId, customerId);

        verify(customerRepository).updateProfileImageId(profileImageId, customerId);
    }

    @Test
    void findCustomerByEmail() {
    }
    @Test
    void findCustomerByName() {
    }
    @Test
    void findCustomerByAge() {
    }
}