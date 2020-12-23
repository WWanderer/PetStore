package com.example.PetStore.controllers;

import com.example.PetStore.entities.Customer;
import com.example.PetStore.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(path = "/customers")
    public ResponseEntity<List<Customer>> listCustomer() {
        return new ResponseEntity<>(customerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/customers/{id}")
    public ResponseEntity<Customer> listCustomer(@PathVariable Long id) {
        return new ResponseEntity<Customer>(customerRepository.findById(id).orElseThrow(() -> new RuntimeException()), HttpStatus.OK);
    }

    @PostMapping(path = "/customers/")
    public ResponseEntity<String> listCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
