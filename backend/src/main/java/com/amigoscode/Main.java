package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.amigoscode.customer.Gender;
import com.amigoscode.s3.S3Buckets;
import com.amigoscode.s3.S3Service;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
      /*Never do this:
        CustomerService customerService =
                new CustomerService(new CustomerDataAccessService());
        CustomerController customerController =
                new CustomerController(customerService);
        */
        /* To print all the Beans:
        ConfigurableApplicationContext applicationContext = 
                SpringApplication.run(Main.class, args);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        } */

        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            createRandomCustomer(customerRepository, passwordEncoder);
            //testBucketUploadAndDownload(s3Service, s3Buckets);
        };
    }

    private static void testBucketUploadAndDownload(S3Service s3Service, S3Buckets s3Buckets) {
        s3Service.putObject(
                s3Buckets.getCustomer(),
                "hello/spartak",
                "Hello World".getBytes()
        );

        byte[] obj = s3Service.getObject(s3Buckets.getCustomer(), "foo");
        System.out.println("Hooray: " + new String(obj));
    }

    private static void createRandomCustomer(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        var faker = new Faker();
        Name name = faker.name();
        Random random = new Random();

        String firstName = name.firstName();
        String lastName = name.lastName();
        int age = random.nextInt(16, 99);
        Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;

        Customer customer = new Customer(
                firstName + " " + lastName,
                firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
                passwordEncoder.encode(UUID.randomUUID().toString()), age,
                gender);
        customerRepository.save(customer);
    }

/*@Bean("foo") // '("foo")' - to change the name of Bean
public Foo getFoo() {
    return new Foo("bar");
}

record Foo(String name) {}*/
}

/*@GetMapping("/greet")
public GreetResponse greet(
        @RequestParam(value = "name", required = false) String name) {
    String greetMassage = name == null ||  name.isBlank() ? "Hello" : "Hello " + name;
    GreetResponse response = new GreetResponse(
            greetMassage,
            List.of("Java", "Golang", "Javascript"),
            new Person("Alex", 28, 30_000)
    );
    return response;
}

record Person(String name, int age, double savings) {
}

record GreetResponse(
        String greet,
        List<String> favProgrammingLanguages,
        Person person) {
}
class GreetResponse {
private final String greet;

public GreetResponse(String greet) {
    this.greet = greet;
}

public String getGreet() {
    return greet;
}

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GreetResponse that = (GreetResponse) o;
    return Objects.equals(getGreet(), that.getGreet());
}

@Override
public int hashCode() {
    return Objects.hash(getGreet());
}

@Override
public String toString() {
    return "GreetResponse{" +
            "greet='" + greet + '\'' +
            '}';
}
}*/