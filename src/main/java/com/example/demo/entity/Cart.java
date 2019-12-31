package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class Cart {
	
	private Long id;
	private List<ItemCart> itemCarts;

}
