package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemCart;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.total.Grupo;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ItemRepo;
import com.example.demo.repository.UsuarioRepo;
import com.mongodb.annotations.ThreadSafe;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
@ThreadSafe
public class GrupoController {
	
	private UsuarioRepo usuarioRepo;
	private CartRepo cartRepo;
	private ItemRepo itemRepo;
	
	@Autowired // Injecting cartRepo and itemRepo
	public GrupoController(CartRepo cartRepo, UsuarioRepo usuarioRepo
			, ItemRepo itemRepo) {
		this.cartRepo = cartRepo;
		this.usuarioRepo = usuarioRepo;
		this.itemRepo = itemRepo;
	}
	@GetMapping("/grupos")
	public List<Grupo> getGrupos(){
		List<Grupo> listGrupo = new ArrayList<Grupo>();
	
		// get lista de usuarios
		List<Usuario> listUsuarios = usuarioRepo.findAll();
		
		for (Usuario usuario : listUsuarios) {
			Double valorTotal = 0.0;
			Optional<Cart> optCart = cartRepo.findById(usuario.getId());
			if (optCart.isPresent()) {
				for (ItemCart itemCart : optCart.get().getItemCarts()) {
					Optional<Item> optItem = itemRepo.findById(itemCart.getItem().getId());
					Item item = optItem.get();
					if (item!=null) {
						valorTotal += item.getValor() * itemCart.getQtde();
					}
				}
				listGrupo.add(new Grupo(usuario.getNome(), valorTotal));
			}			
		}
		
		listGrupo.forEach(System.out::println);
		
		listGrupo.sort(Comparator.comparing(Grupo::getValorTotal));
		listGrupo.forEach(System.out::println);
		
		
		return listGrupo;
	}

}
