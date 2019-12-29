package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Cart;

public interface CartRepo extends MongoRepository<Cart, Long> {

}
