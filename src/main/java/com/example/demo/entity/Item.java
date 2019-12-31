package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
	
	 //ID, NOME e VALOR. 
	private Long id;
	private String nome;
	private Double valor;
	
}
