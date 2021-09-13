package com.customergroup.customer;

import com.customergroup.application.domain.Contact;
import com.customergroup.application.domain.Customer;
import com.customergroup.data.ContactRespository;
import com.customergroup.data.CustomerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
/*
    The purpose of the CustomerConfig is to preload information into the
    database for testing purposes.

 */
public class CustomerConfig {

    @Bean
    CommandLineRunner customerLineRunner(CustomerRespository customerRespository,
                                         ContactRespository contactRespository){
        return args -> {
            Customer apple = new Customer(
                    "Apple",
                    "Somewhere in FreedomLand",
                    "FreedomLand"
            );
            customerRespository.saveAll(List.of(apple));

            Contact peter = new Contact(
                    "Peter",
                    "+61-412-123-456",
                    "peter@gmail.com",
                    "positionOne"
            );

            Contact pineapple = new Contact(
                    "Pineapple",
                    "+61-412-123-456",
                    "pineapple@gmail.com",
                    "positionTwo"
            );

            contactRespository.saveAll(List.of(peter, pineapple));
        };
    }
}