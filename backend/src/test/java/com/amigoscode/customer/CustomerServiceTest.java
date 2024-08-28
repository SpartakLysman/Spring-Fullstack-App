package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import com.amigoscode.s3.S3Buckets;
import com.amigoscode.s3.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDAO customerDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private S3Service s3Service;

    @Mock
    private S3Buckets s3Buckets;

    private CustomerService underTest;

    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(
                customerDAO,
                customerDTOMapper,
                passwordEncoder,
                s3Service,
                s3Buckets
        );
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();

        verify(customerDAO).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        long id = 1;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerDTO expected = customerDTOMapper.apply(customer);

        CustomerDTO actual = underTest.getCustomer(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenGetCustomerReturnsEmptyOptional() {
        long id = 10;

        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        String email = "alex@gmail.com";

        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "ALex", email, "password", 19, Gender.MALE
        );

        String passwordHash = "fv444fdb6r7egr344";

        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);
        underTest.addCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getPassword()).isEqualTo(passwordHash);
    }

    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        String email = "alex@gmail.com";

        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "ALex", email, "password", 19, Gender.MALE
        );

        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        verify(customerDAO, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        long id = 10;
        String email = "alex@gmail.com";

        when(customerDAO.existsCustomerWithId(id)).thenReturn(true);

        underTest.deleteCustomerById(id);

        verify(customerDAO).deleteCustomerById(id);
    }

    @Test
    void willThrowWhenDeleteCustomerByIdNotExists() {
        long id = 10;
        String email = "alex@gmail.com";

        when(customerDAO.existsCustomerWithId(id)).thenReturn(false);

        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));

        verify(customerDAO, never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomerProperties() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail, 23);

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);

        underTest.updateCustomer(id, updateRequest);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null, null);

        underTest.updateCustomer(id, updateRequest);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(false);

        underTest.updateCustomer(id, updateRequest);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null, 22);

        underTest.updateCustomer(id, updateRequest);

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@amigoscode";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail, null);

        when(customerDAO.existsCustomerWithEmail(newEmail)).thenReturn(true);

        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        verify(customerDAO, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        long id = 10;

        Customer customer = new Customer(id, "Alex", "alex@gmail.com", "password", 19, Gender.MALE);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(), customer.getEmail(), customer.getAge());

        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        verify(customerDAO, never()).updateCustomer(any());
    }

    @Test
    void canUploadProfileImage() {
        long customerId = 10;

        when(customerDAO.existsCustomerWithId(customerId)).thenReturn(true);

        byte[] bytes = "Hello World".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("file", bytes);

        String bucket = "customer-bucket";
        when(s3Buckets.getCustomer()).thenReturn(bucket);

        underTest.uploadCustomerProfileImage(
                customerId, multipartFile
        );

        ArgumentCaptor<String> profileImageIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(customerDAO).updateCustomerProfileImageId(profileImageIdArgumentCaptor.capture(), eq(customerId));

        verify(s3Service).putObject(
                bucket,
                "profile_images/%s/%s".formatted(customerId, profileImageIdArgumentCaptor.getValue()),
                bytes
        );
    }

    @Test
    void cannotUploadProfileImageWhenCustomerDoesNotExists() {
        long customerId = 10;

        when(customerDAO.existsCustomerWithId(customerId)).thenReturn(false);

        assertThatThrownBy(() -> underTest.uploadCustomerProfileImage(customerId, mock(MultipartFile.class))
        )
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [" + customerId + "] not found");

        verify(customerDAO).existsCustomerWithId(customerId);

        verifyNoMoreInteractions(customerDAO);

        verifyNoInteractions(s3Buckets);

        verifyNoInteractions(s3Service);
    }

    @Test
    void cannotUploadProfileImageWhenExceptionIsThrown() throws IOException {
        long customerId = 10;

        when(customerDAO.existsCustomerWithId(customerId)).thenReturn(true);

        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getBytes()).thenThrow(IOException.class);

        String bucket = "customer-bucket";
        when(s3Buckets.getCustomer()).thenReturn(bucket);

        assertThatThrownBy(() -> {
            underTest.uploadCustomerProfileImage(customerId, multipartFile);
        }).isInstanceOf(RuntimeException.class)
                .hasMessage("failed to upload profile image")
                .hasRootCauseInstanceOf(IOException.class);

        verify(customerDAO, never()).updateCustomerProfileImageId(any(), any());
    }

    @Test
    void canDownloadProfileImage() {
        long customerId = 10;
        String profileImageId = "2222";
        Customer customer = new Customer(
                customerId,
                "Alex",
                "alex@gmail.com",
                "password",
                19,
                Gender.MALE,
                profileImageId
        );

        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(customer));

        String bucket = "customer-bucket";
        when(s3Buckets.getCustomer()).thenReturn(bucket);

        byte[] expectedImage = "image".getBytes();

        when(s3Service.getObject(
                bucket,
                "profile_images/%s/%s".formatted(customerId, profileImageId))
        ).thenReturn(expectedImage);

        byte[] actualImage = underTest.getCustomerProfileImage(customerId);

        assertThat(actualImage).isEqualTo(expectedImage);
    }

    @Test
    void cannotDownloadWhenNoProfileImageId() {
        long customerId = 10;
        Customer customer = new Customer(
                customerId,
                "Alex",
                "alex@gmail.com",
                "password",
                19,
                Gender.MALE
        );
        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> underTest.getCustomerProfileImage(customerId)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] profile image not found".formatted(customerId));

        verifyNoInteractions(s3Buckets);
        verifyNoInteractions(s3Service);
    }

    @Test
    void cannotDownloadProfileImageWhenCustomerDoesNotExists() {
        long customerId = 10;

        when(customerDAO.selectCustomerById(customerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getCustomerProfileImage(customerId)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(customerId));

        verifyNoInteractions(s3Buckets);
        verifyNoInteractions(s3Service);
    }

    @Test
    void getSortedCustomersByIdAsc() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "John", "john@example.com", "password", 25, Gender.MALE, "image1"),
                new Customer(2L, "Jane", "jane@example.com", "password", 30, Gender.FEMALE, null)
        );

        List<CustomerDTO> expectedDTOs = customers.stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        when(customerDAO.selectAllCustomers(SortCriteria.ID, SortDirection.ASC)).thenReturn(customers);

        List<CustomerDTO> result = underTest.getSortedCustomers(SortCriteria.ID, SortDirection.ASC);

        assertThat(result).isEqualTo(expectedDTOs);
    }

    @Test
    void getSortedCustomersByAgeDesc() {
        // Define test data
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "John", "john@example.com", "password", 25, Gender.MALE, "image1"),
                new Customer(2L, "Jane", "jane@example.com", "password", 30, Gender.FEMALE, null)
        );

        List<CustomerDTO> expectedDTOs = customers.stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        when(customerDAO.selectAllCustomers(SortCriteria.AGE, SortDirection.DESC)).thenReturn(customers);

        List<CustomerDTO> result = underTest.getSortedCustomers(SortCriteria.AGE, SortDirection.DESC);

        assertThat(result).isEqualTo(expectedDTOs);
    }

    @Test
    void getSortedCustomersByImageAsc() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "John", "john@example.com", "password", 25, Gender.MALE, null),
                new Customer(2L, "Jane", "jane@example.com", "password", 30, Gender.FEMALE, "image1")
        );

        List<CustomerDTO> expectedDTOs = customers.stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());

        when(customerDAO.selectAllCustomers(SortCriteria.IMAGE, SortDirection.ASC)).thenReturn(customers);

        List<CustomerDTO> result = underTest.getSortedCustomers(SortCriteria.IMAGE, SortDirection.ASC);

        assertThat(result).isEqualTo(expectedDTOs);
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

















