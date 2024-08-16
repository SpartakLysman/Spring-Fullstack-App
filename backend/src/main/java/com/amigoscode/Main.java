package com.amigoscode;

import com.amigoscode.customer.Customer;
import com.amigoscode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        /*
        Never do this:
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
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            var faker = new Faker();
            Name name = faker.name();
            Random random = new Random();

            String firstName = name.firstName();
            String lastName = name.lastName();

            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@amigoscode.com",
                    random.nextInt(16, 99)
            );
            customerRepository.save(customer);
        };
    }




    /*@Bean("foo") // '("foo")' - to change the name of Bean
    public Foo getFoo() {
        return new Foo("bar");
    }

    record Foo(String name) {}*/
}

/*
    @GetMapping("/greet")
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