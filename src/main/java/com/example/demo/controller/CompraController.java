package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemCart;
import com.example.demo.entity.total.Compra;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ItemRepo;
import com.mongodb.annotations.ThreadSafe;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
@ThreadSafe
public class CompraController {
	
	private CartRepo cartRepo;
	private ItemRepo itemRepo;
	
	@Autowired // Injecting cartRepo and itemRepo
	public CompraController(CartRepo cartRepo, ItemRepo itemRepo) {
		this.cartRepo = cartRepo;
		this.itemRepo = itemRepo;
	}
	
	@GetMapping("/compras/{carrinhoId}")
	public List<Compra> getCompras(@PathVariable long carrinhoId){
		Optional<Cart> optCart= cartRepo.findById(carrinhoId);
		Cart cart = optCart.get();
		
		if (cart==null) {
			System.out.println("Cart Null");
		}
		
		List<Compra> listCompras = new ArrayList<Compra>();
		
		// transform cart to list compra
		for (ItemCart itemCart : cart.getItemCarts()) {
			System.out.println("loop itemcart");
			// get updated value
			Optional<Item> optItem = itemRepo.findById(itemCart.getItem().getId());
			Item item = optItem.get();
			if (item!=null) {
				// convert to compra
				Compra compra = new Compra(item.getNome(), item.getValor(), 
						itemCart.getQtde(),
						item.getValor() * itemCart.getQtde());	
				listCompras.add(compra);
			}
		}
		
		// order items
		listCompras.forEach(System.out::println);
		
		listCompras.sort(Comparator.comparing(Compra::getNome)
				.thenComparing(Compra::getValorUnitario)
				.thenComparing(Compra::getValorTotal));
		
		listCompras.forEach(System.out::println);
		
		return listCompras;
	}
	
	
}
