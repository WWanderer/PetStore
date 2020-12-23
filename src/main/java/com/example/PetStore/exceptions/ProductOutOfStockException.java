package com.example.PetStore.exceptions;

public class ProductOutOfStockException extends RuntimeException {
    public ProductOutOfStockException(Long id) {
        super("Product " + id + " appears to be out of stock");
    }

    public ProductOutOfStockException(String name) {
        super("Product called" + name + " appears to be out of stock");
    }

}
