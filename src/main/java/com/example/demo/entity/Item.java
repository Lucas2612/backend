package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Item {
	
	 //ID, NOME e VALOR. 
	private Long id;
	private String nome;
	private String valor;
	
	public void teste() {
		
	}
}
