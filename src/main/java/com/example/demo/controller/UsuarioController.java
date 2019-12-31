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
import com.example.demo.entity.ItemCart;
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
	public Usuario getUsuario(@PathVariable long usuarioId) {
		Optional<Usuario> tempUsuario = usuarioRepo.findById(usuarioId);
		return tempUsuario.get();
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
		
		// get carts e delete items
		List<Cart> cartList = cartRepo.findAll();
		for (Cart cart : cartList) {
			if (cart.getId().equals(usuarioId)) {
				cartList.remove(cart);
			}
		}
		usuarioRepo.deleteById(usuarioId);		
	}
	
	@PutMapping("/usuarios")
	public Usuario updateItem(@RequestBody Usuario updatedUsuario) {
		
		usuarioRepo.save(updatedUsuario);
		
		return updatedUsuario;
	}
}
