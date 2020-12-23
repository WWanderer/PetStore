package com.example.PetStore.controllers;

import com.example.PetStore.repositories.ProductRepository;
import com.example.PetStore.entities.Product;
import com.example.PetStore.exceptions.ProductNotFoundException;
import com.example.PetStore.exceptions.ProductOutOfStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    public final ProductRepository productRepository;
    public static final Logger log = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    List<Product> all() {
        log.info("get request for all products");
        return productRepository.findAll();
    }

    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct) {
        log.info("post request to create a product : " + newProduct.toString());
        return productRepository.save(newProduct);
    }

    @PostMapping("/products/{id}")
    Product orderProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    int qtt = product.getQuantity();
                    if (qtt <= 0) {
                        log.info("product "+ id +" out of stock");
                        throw new ProductOutOfStockException(id);
                    } else {
                        product.setQuantity(qtt - 1);
                        log.info(product.toString());
                        return productRepository.save(product);
                    }
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id) throws RuntimeException {
        log.info("get request to find a product by id : " + id);
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping("/products/name/{name}")
    Product one(@PathVariable String name) {
        log.info("get request to find a product by name : " + name);
        return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
    }

    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product newp, @PathVariable Long id) {
        log.info("put request to update or create a product by id : " + id);
        return productRepository.findById(id)
                .map(p -> {
                    p.setName(newp.getName());
                    p.setPrice(newp.getPrice());
                    p.setQuantity(newp.getQuantity());
                    return productRepository.save(p);
                })
                .orElseGet(() -> {
                    newp.setId(id);
                    return productRepository.save(newp);
                });
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        log.info("delete request for product : " + id);
        productRepository.deleteById(id);
    }
}

