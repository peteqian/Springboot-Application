package com.productgroup.configuration;

import com.productgroup.data.ProductDetailRepository;
import com.productgroup.data.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.productgroup.application.domain.Product;
import com.productgroup.application.domain.ProductDetail;

import java.util.List;

@Configuration
public class ProductConfiguration {
    @Bean
    CommandLineRunner productLineRunner (ProductRepository productRepository,
                                         ProductDetailRepository productDetailRepository){
        return args -> {
            Product productOne = new Product(
                    "Beverage",
                    "Water",
                    1.05,
                    10
            );
            Product productTwo = new Product(
                    "Food",
                    "Hotdog",
                    10.00,
                    5
            );

            productRepository.save(productOne);
            productRepository.save(productTwo);

            ProductDetail productDetailOne = new ProductDetail(
                    "It is a fluid",
                    "Everyone needs it in their lives"
            );

            ProductDetail productDetailTwo = new ProductDetail(
                    "Large 15 pepperoni",
                    "Not from dominos."
            );

            productDetailRepository.save(productDetailOne);
            productDetailRepository.save(productDetailTwo);
        };
    }
}