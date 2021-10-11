package com.customergroup.infrastructure.configuration;

import com.customergroup.domain.Contact;
import com.customergroup.domain.Customer;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.infrastructure.repository.CustomerRespository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    "CompanyApple",
                    "Somewhere in FreedomLand",
                    "U.S.A");

            Customer bravo = new Customer("CompanyBravo",
                    "88 Pitt St, Sydney, NSW, 2000",
                    "Australia");

            Customer charlie = new Customer("CompanyCharlie",
                    "Bit my finger St, Sydney, NSW 2000",
                    "Australia");

            Customer delta = new Customer("CompanyDelta",
                    " 71 Bank St, North Sydney, NSW 2060",
                    "Australia");

            Customer echo = new Customer("CompanyEcho",
                    "84 Pitt St, Sydney, NSW 2000",
                    "Australia");

            Customer epic = new Customer("Epic Games",
                    "620 Crossroads Blvd., Cary, NC",
                    "USA");

            Customer tesla  = new Customer("Tesla",
                    "3500 Deer Creek Road Palo Alto, CA 94304",
                    "USA");

            Customer valve = new Customer("Valve",
                    "10400 Northeast Fourth Street Floor 14, Bellevue, WA 98004 USA",
                    "USA");

            Contact gabe = new Contact("Gabe Newell",
                    "12-12-12",
                    "valve@gmail.com",
                    "CEO");

            Contact peter = new Contact("Peter",
                    "+61-412-123-456",
                    "peter@gmail.com",
                    "positionOne");

            Contact pineapple = new Contact("Pineapple",
                    "+61-412-123-456",
                    "pineapple@gmail.com",
                    "positionTwo");

            Contact timSweeney = new Contact("Tim Sweeney",
                    "123-123-123",
                    "epic@apple.killer.com",
                    "USA");

            apple.setContact(peter);
            peter.setCustomer(apple);

            bravo.setContact(pineapple);
            pineapple.setCustomer(bravo);

            valve.setContact(gabe);
            gabe.setCustomer(valve);

            epic.setContact(timSweeney);
            timSweeney.setCustomer(epic);

            customerRespository.save(apple);
            customerRespository.save(bravo);
            customerRespository.save(charlie);
            customerRespository.save(delta);
            customerRespository.save(echo);
            customerRespository.save(epic);
            customerRespository.save(tesla);
            customerRespository.save(valve);

            contactRespository.save(gabe);
            contactRespository.save(peter);
            contactRespository.save(pineapple);
            contactRespository.save(timSweeney);

        };
    }
}
