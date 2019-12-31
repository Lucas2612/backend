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
import com.example.demo.repository.CartRepo;
import com.mongodb.annotations.ThreadSafe;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
@ThreadSafe
public class CartController {

	private CartRepo cartRepo;
	
	@Autowired // Injecting cartRepo
	public CartController(CartRepo cartRepo) {
		this.cartRepo = cartRepo;
	}
	
	// exposing Select All
	@GetMapping("/carrinhos")
	public List<Cart> findAll() {
		return cartRepo.findAll();
	}
	
	@GetMapping("/carrinhos/{carrinhoId}")
	public Cart getCart(@PathVariable long carrinhoId) {
		System.out.println("GET id: " + carrinhoId);
		Optional<Cart> tempCart= cartRepo.findById(carrinhoId);
		if (tempCart.isPresent()) return tempCart.get();
		
		return null;
	}
	
	// exposing insert new cart
	@PostMapping("/carrinhos")
	public Cart addCart(@RequestBody Cart newCart) {
		System.out.println("Post carrinhos");
		cartRepo.insert(newCart);
		return newCart;
	}
	
	@DeleteMapping("/carrinhos/{cartId}")
	public void deleteCart(@PathVariable Long cartId) {
		cartRepo.deleteById(cartId);		
	}
	
	@PutMapping("/carrinhos")
	public Cart updateCart(@RequestBody Cart updatedCart) {
		
		cartRepo.save(updatedCart);
		
		return updatedCart;
	}
	
}
