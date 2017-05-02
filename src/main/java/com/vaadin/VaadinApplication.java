package com.vaadin;

import com.vaadin.dao.CustomerRepository;
import com.vaadin.entities.Customer;
import com.vaadin.entities.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class VaadinApplication {

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(VaadinApplication.class, args);
    }


    @PostConstruct
    public void addCustomers() {
        Customer customer = new Customer("Bobik", "Kowalski", Gender.MALE, "bob@kow.pl", LocalDate.of(1991, 03, 22));
        Customer customer2 = new Customer("Zosia", "Samosia", Gender.FEMALE, "zocha@sam.pl", LocalDate.of(1991, 03, 22));

        customerRepository.save(customer);
        customerRepository.save(customer2);
    }
}
