package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class ItemController {

	private ItemRepo itemRepo;
	
	@Autowired // Injecting itemRepo
	public ItemController(ItemRepo itemRepo) {
		this.itemRepo = itemRepo;
	}
	
	// exposing Select All
	@GetMapping("/items")
	public List<Item> findAll() {
		return itemRepo.findAll();
	}
	
	// exposing insert new item
	@PostMapping("/items")
	public Item addEmployee(@RequestBody Item newItem) {
		System.out.println("Inserindo: " + newItem);
		itemRepo.insert(newItem);
		return newItem;
	}
	
	@DeleteMapping("/items/{itemId}")
	public void deleteItem(@PathVariable Long itemId) {
		
		if (itemRepo.existsById(itemId)) {
			System.out.println("Existe ID");
		}else {
			System.out.println("Não existe ID");
		}

		itemRepo.deleteById(itemId);		
	}
	
	@PutMapping("/items")
	public Item updateItem(@RequestBody Item updatedItem) {
		
		itemRepo.save(updatedItem);
		
		return updatedItem;
	}
	
}
