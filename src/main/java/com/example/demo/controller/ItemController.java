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
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemCart;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ItemRepo;
import com.mongodb.annotations.ThreadSafe;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
@ThreadSafe
public class ItemController {

	private ItemRepo itemRepo;
	private CartRepo cartRepo;
	
	@Autowired // Injecting itemRepo
	public ItemController(ItemRepo itemRepo, CartRepo cartRepo) {
		this.itemRepo = itemRepo;
		this.cartRepo = cartRepo;
	}
	
	// exposing Select All
	@GetMapping("/items")
	public List<Item> findAll() {
		return itemRepo.findAll();
	}
	
	// get 1 item to update name
	@GetMapping("/items/{itemId}")
	public Item getItem(@PathVariable Long itemId) {
		Optional<Item> tempItem = itemRepo.findById(itemId);
		
		if (tempItem.isPresent()) {
			return tempItem.get();
		}
		return null;
	}
	
	
	// exposing insert new item
	@PostMapping("/items")
	public Item addItem(@RequestBody Item newItem) {
		System.out.println("Inserindo: " + newItem);
		itemRepo.insert(newItem);
		return newItem;
	}
	
	@DeleteMapping("/items/{itemId}")
	public void deleteItem(@PathVariable Long itemId) {
		
		// get carts e delete items
		List<Cart> cartList = cartRepo.findAll();
		for (Cart cart : cartList) {
			for (ItemCart itemCart : cart.getItemCarts()) {
				if (itemCart.getItem().getId().equals(itemId)) {
					// remove
					cart.getItemCarts().remove(itemCart);
					break;
				}
			}
			// update
			if (cart.getItemCarts().size()==0) {
				cartRepo.delete(cart);
			}else {
				cartRepo.save(cart);
			}
		}
		
		
		itemRepo.deleteById(itemId);		
	}
	
	@PutMapping("/items")
	public Item updateItem(@RequestBody Item updatedItem) {
		
		itemRepo.save(updatedItem);
		
		return updatedItem;
	}
	
}
