package com.amigoscode.customer;

import com.amigoscode.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessService(getJdbcTemplate(), customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8),
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        List<Customer> actual = underTest.selectAllCustomers();

        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        long id = -1;

        var actual = underTest.selectCustomerById(id);

        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8),
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        List<Customer> actual = underTest.selectAllCustomers();

        assertThat(actual).isNotEmpty();
    }

    @Test
    void existsCustomerWithEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        boolean actual = underTest.existsCustomerWithEmail(email);

        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent();
    }

    @Test
    void existsCustomerWithEmailReturnsFalseWhenDoesNotExists() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);

        boolean actual = underTest.existsCustomerWithEmail(email);

        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerWithIdReturnsFalseWhenIdNotPresent() {
        long id = -1;

        var actual = underTest.existsCustomerWithId(id);

        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomerById(id);

        Optional<Customer> actual = underTest.selectCustomerById(id);
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateCustomerName() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newName = "IVAN";

        Customer update = new Customer();
        update.setId(id);
        update.setName(newName);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(newName); // change
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newEmail = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);

        Customer update = new Customer();
        update.setId(id);
        update.setEmail(newEmail);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(newEmail); // change
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void updateCustomerAge() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var newAge = 100;

        Customer update = new Customer();
        update.setId(id);
        update.setAge(newAge);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(newAge); // change
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }

    @Test
    void willUpdateAllPropertiesCustomer() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);
        update.setName("foo");
        String newEmail = UUID.randomUUID().toString();
        update.setEmail(newEmail);
        update.setAge(22);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(updated -> {
            assertThat(updated.getId().equals(id));
            assertThat(updated.getGender().equals(Gender.MALE));
            assertThat(updated.getName().equals("foo"));
            assertThat(updated.getEmail().equals(newEmail));
            assertThat(updated.getAge().equals(22));
        });
    }

    @Test
    void willNotUpdateWhenNothingToUpdate() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer update = new Customer();
        update.setId(id);

        underTest.updateCustomer(update);

        Optional<Customer> actual = underTest.selectCustomerById(id);

        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });
    }


    @Test
    void canUpdateProfileImageId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID().toString().substring(0, 8);
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                "password", 20,
                Gender.MALE);
        underTest.insertCustomer(customer);

        long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.updateCustomerProfileImageId("2222", id);

        Optional<Customer> customerOptional = underTest.selectCustomerById(id);
        assertThat(customerOptional).isPresent().hasValueSatisfying(
                c -> assertThat(c.getProfileImageId()).isEqualTo("2222"));
    }

    @Test
    void selectCustomerByEmail() {
    }

    @Test
    void selectCustomerByName() {
    }

    @Test
    void selectCustomerByAge() {
    }

//    @Test
//    void selectAllCustomersSortedByAgeAsc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.AGE, SortDirection.ASC);
//
//        // Assert the results
//        assertThat(customers).extracting(Customer::getAge).containsExactly(25, 30, 35);
//    }
//
//
//    @Test
//    void selectAllCustomersSortedByAgeDesc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.ID, SortDirection.DESC);
//
//        // Assert the results
//        assertThat(customers).extracting(Customer::getAge).containsExactly(35, 30, 25);
//    }
//
//
//    @Test
//    void selectAllCustomersSortedByProfileImageDesc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.IMAGE, SortDirection.DESC);
//
//        // Assert the results
//        assertThat(customers).extracting(Customer::getProfileImageId).containsExactly(null, "image1", "image2");
//    }
//
//    @Test
//    void selectAllCustomersSortedByProfileImageAsc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.IMAGE, SortDirection.ASC);
//
//        // Assert the results
//        assertThat(customers).extracting(Customer::getProfileImageId).containsExactly("image1", "image2", null);
//    }
//
//
//    @Test
//    void selectAllCustomersSortedByGenderAsc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//        Customer diana = new Customer("Diana", "diana@example.com", "password", 28, Gender.FEMALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//        underTest.insertCustomer(diana);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.GENDER, SortDirection.ASC);
//
//        // Extract the gender values from the result
//        List<Gender> genders = customers.stream()
//                .map(Customer::getGender)
//                .collect(Collectors.toList());
//
//        // Assert the results
//        assertThat(genders).containsExactly(Gender.FEMALE, Gender.FEMALE, Gender.MALE, Gender.MALE);
//    }
//
//    @Test
//    void selectAllCustomersSortedByGenderDesc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//        Customer diana = new Customer("Diana", "diana@example.com", "password", 28, Gender.FEMALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//        underTest.insertCustomer(diana);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.GENDER, SortDirection.DESC);
//
//        // Extract the gender values from the result
//        List<Gender> genders = customers.stream()
//                .map(Customer::getGender)
//                .collect(Collectors.toList());
//
//        // Assert the results
//        assertThat(genders).containsExactly(Gender.MALE, Gender.MALE, Gender.FEMALE, Gender.FEMALE);
//    }
//
//
//    @Test
//    void selectAllCustomersSortedByIdAsc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.ID, SortDirection.ASC);
//
//        // Extract the id values from the result
//        List<Long> ids = customers.stream()
//                .map(Customer::getId)
//                .collect(Collectors.toList());
//
//        // Assert the results (ensure they are sorted by ID in ascending order)
//        assertThat(ids).isSortedAccordingTo(Long::compare);
//    }
//
//    @Test
//    void selectAllCustomersSortedByIdDesc() {
//        // Insert test data
//        Customer alice = new Customer("Alice", "alice@example.com", "password", 25, Gender.FEMALE);
//        Customer bob = new Customer("Bob", "bob@example.com", "password", 30, Gender.MALE);
//        Customer charlie = new Customer("Charlie", "charlie@example.com", "password", 35, Gender.MALE);
//
//        underTest.insertCustomer(alice);
//        underTest.insertCustomer(bob);
//        underTest.insertCustomer(charlie);
//
//        // Execute the test
//        List<Customer> customers = underTest.selectAllCustomers(SortCriteria.ID, SortDirection.DESC);
//
//        // Extract the id values from the result
//        List<Long> ids = customers.stream()
//                .map(Customer::getId)
//                .collect(Collectors.toList());
//
//        // Assert the results (ensure they are sorted by ID in descending order)
//        assertThat(ids).isSortedAccordingTo(Comparator.reverseOrder());
//    }
}