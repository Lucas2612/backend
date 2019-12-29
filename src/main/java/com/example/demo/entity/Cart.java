package com.example.demo.entity;

import java.util.List;

import lombok.Data;

@Data
public class Cart {
	
	private long idUsuario;
	private List<ItemCart> itemCarts;

}
