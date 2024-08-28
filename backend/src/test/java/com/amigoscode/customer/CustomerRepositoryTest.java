package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainers;
import com.amigoscode.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// disabled the embedded database and witch to the container one
@Import({TestConfig.class})
class CustomerRepositoryTest extends AbstractTestcontainers {

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.save(customer);

        var actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);

        var actual = underTest.existsCustomerByEmail(email);

        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.save(customer);

        long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var actual = underTest.existsCustomerById(id);

        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByIdWhenIdNotPresent() {
        long id = -1;

        var actual = underTest.existsCustomerById(id);

        assertThat(actual).isFalse();
    }

    @Test
    void canUpdateProfileImageId() {
        String email = "email";
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.save(customer);

        long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

       underTest.updateProfileImageId("2222", id);

        Optional<Customer> customerOptional = underTest.findById(id);
        assertThat(customerOptional).isPresent().hasValueSatisfying(
                c -> assertThat(c.getProfileImageId()).isEqualTo("2222"));
    }

    @Test
    void findCustomerByName() {
    }
    @Test
    void findCustomerByAge() {
    }
    @Test
    void findCustomerByEmail() {
    }



}