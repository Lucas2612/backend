package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.Item;

public interface ItemRepo extends MongoRepository<Item, Long> {

}
