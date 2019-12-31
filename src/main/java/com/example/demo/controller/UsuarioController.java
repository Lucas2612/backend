package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.UsuarioRepo;
import com.mongodb.annotations.ThreadSafe;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
@ThreadSafe
public class UsuarioController {

	private UsuarioRepo usuarioRepo;
	private CartRepo cartRepo;
	
	@Autowired // Injecting UsuarioRepo
	public UsuarioController(UsuarioRepo usuarioRepo, CartRepo cartRepo) {
		this.usuarioRepo = usuarioRepo;
		this.cartRepo = cartRepo;
	}
	
	// exposing Select All
	@GetMapping("/usuarios")
	public List<Usuario> findAll() {
		return usuarioRepo.findAll();
	}
	
	@GetMapping("/usuarios/{usuarioId}")
	public Usuario getUsuario(@PathVariable Long usuarioId) {
		Optional<Usuario> tempUsuario = usuarioRepo.findById(usuarioId);
		if (tempUsuario.isPresent()) return tempUsuario.get();
		return null;
	}
	
	// exposing insert new item
	@PostMapping("/usuarios")
	public Usuario addUsuario(@RequestBody Usuario novoUsuario) {
		System.out.println("Inserindo: " + novoUsuario);
		usuarioRepo.insert(novoUsuario);
		return novoUsuario;
	}
	
	@DeleteMapping("/usuarios/{usuarioId}")
	public void deleteUsuario(@PathVariable Long usuarioId) {
		
		// delete cart if exists
		Optional<Cart> optCart= cartRepo.findById(usuarioId);
		if (optCart.isPresent()) {
			cartRepo.delete(optCart.get());
		}
		
		usuarioRepo.deleteById(usuarioId);		
	}
	
	@PutMapping("/usuarios")
	public Usuario updateItem(@RequestBody Usuario updatedUsuario) {
		
		usuarioRepo.save(updatedUsuario);
		
		return updatedUsuario;
	}
}
