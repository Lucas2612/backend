package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usuario {

	// ID, NOME e EMAIL 
	private Long id;
	private String nome;
	private String email;
}
