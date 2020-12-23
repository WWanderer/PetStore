package com.example.PetStore.config;

import com.example.PetStore.repositories.CustomerRepository;
import com.example.PetStore.repositories.ProductRepository;
import com.example.PetStore.entities.Customer;
import com.example.PetStore.entities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadData {
    public static final Logger log = LoggerFactory.getLogger(LoadData.class);

    @Bean
    CommandLineRunner initdb(ProductRepository productRepository, CustomerRepository customerRepository) {
        return args -> {
            log.info("Cleaning db: ");
            productRepository.deleteAll();
            log.info("Preloading " + productRepository.save(new Product("Toto", 7.99f, 0)));
            log.info("Preloading " + productRepository.save(new Product("Babar", 12.99f, 5)));
            log.info("Preloading " + customerRepository.save(new Customer("Michel", 100f, "michel@mail.com", "password")));
            log.info("Preloading " + customerRepository.save(new Customer("user", 15f, "user@mail.com", "password")));
        };
    }
}
