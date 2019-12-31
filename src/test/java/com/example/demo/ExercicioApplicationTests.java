package com.example.demo;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.CartController;
import com.example.demo.controller.CompraController;
import com.example.demo.controller.GrupoController;
import com.example.demo.controller.ItemController;
import com.example.demo.controller.UsuarioController;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Item;
import com.example.demo.entity.ItemCart;
import com.example.demo.entity.Usuario;
import com.example.demo.entity.total.Compra;
import com.example.demo.entity.total.Grupo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExercicioApplicationTests {
	
	@Autowired 
	MongoTemplate mongoTemplate;
	
	@Autowired
	ItemController itemController;
	
	@Autowired
	UsuarioController usuarioController;
	
	@Autowired
	CartController cartController;
	
	@Autowired
	CompraController compraController;
	
	@Autowired
	GrupoController grupoController;
	
	Item item1 = new Item(1L, "Coffee", new Double(5));
	Item item2 = new Item(2L, "Water", new Double(10));
	Item item3 = new Item(3L, "Rice", new Double(15));
	Item item4 = new Item(4L, "Bean", new Double(20));
	Item item5 = new Item(5L, "Potato", new Double(25));
	
	
	@Test
	@Order(1) 
	public void testItems() {
		
	  //inserting items 
	  itemController.addItem(item1);
	  itemController.addItem(item2); 
	  itemController.addItem(item3);
	  itemController.addItem(item4); 
	  itemController.addItem(item5);
	  
	  // count
	  List<Item> items = itemController.findAll(); assertEquals(items.size(), 5);
	  assertEquals(item1, itemController.getItem(1L)); 
	  
	  itemController.deleteItem(1L);
	  assertNull(itemController.getItem(1L)); 
	  items = itemController.findAll();
	  assertEquals(items.size(), 4);
	  
	}	 
	
	@Test
	@Order(2)
	public void testUsuarios() {
		
		Usuario usuario1 = new Usuario(1L, "John", "john@test.com");
		Usuario usuario2 = new Usuario(2L, "Mary", "mary@test.com");
		Usuario usuario3 = new Usuario(3L, "Bill", "bill@test.com");
		Usuario usuario4 = new Usuario(4L, "Morgan", "morgan@test.com");
		
		// inserting usuarios
		usuarioController.addUsuario(usuario1);	
		usuarioController.addUsuario(usuario2);	
		usuarioController.addUsuario(usuario3);	
		usuarioController.addUsuario(usuario4);	
		
		List<Usuario> usuarios = usuarioController.findAll();
		assertEquals(usuarios.size(), 4);
		
		usuarios = usuarioController.findAll();
		System.out.println(usuarios.size());
		assertEquals(4, usuarios.size());
		
		usuarioController.deleteUsuario(1L);
		assertNull(usuarioController.getUsuario(1L));	
		
		usuarios = usuarioController.findAll();
		System.out.println(usuarios.size());
		assertEquals(usuarios.size(), 3);	
	}
	
	@Test
	@Order(3)
	public void testCart() {
		ItemCart itemCart1 = new ItemCart(item2, 3);
		ItemCart itemCart2 = new ItemCart(item3, 1);
		ItemCart itemCart3 = new ItemCart(item4, 1);
		List<ItemCart> itemCarts = new ArrayList<ItemCart>();
		itemCarts.add(itemCart1);
		itemCarts.add(itemCart2);
		itemCarts.add(itemCart3);
		Cart cart1 = new Cart(2L, itemCarts);
		
		ItemCart itemCart2_1 = new ItemCart(item2, 1);
		ItemCart itemCart2_2 = new ItemCart(item3, 10);
		ItemCart itemCart2_3 = new ItemCart(item4, 1);
		List<ItemCart> itemCarts2 = new ArrayList<ItemCart>();
		itemCarts2.add(itemCart2_1);
		itemCarts2.add(itemCart2_2);
		itemCarts2.add(itemCart2_3);
		Cart cart2 = new Cart(3L, itemCarts2);
		
		cartController.addCart(cart1);
		cartController.addCart(cart2);
		assertEquals(3, cartController.getCart(2).getItemCarts().size());
		
		// delete item remove from the cart
		itemController.deleteItem(2L);
		assertEquals(2, cartController.getCart(2).getItemCarts().size());
		
		// delete usuario delete cart
		usuarioController.deleteUsuario(2L);
		assertNull(cartController.getCart(2));
		
	}
	
	@Test
	@Order(4)
	public void testCompra() {
		List<Compra> compras = compraController.getCompras(3L);
		assertNotNull(compras);
		assertEquals(2, compras.size());
		
		itemController.deleteItem(3L);
		assertEquals(3, compras.size());
		
	}
	
	@Test
	@Order(5)
	public void testGrupo() {
		List<Grupo> grupos = grupoController.getGrupos();
		assertNotNull(grupos);
		
		assertEquals(2, grupos.size());
		
		itemController.deleteItem(3L);
		assertEquals(3, grupos.size());
		
	}
	
	

}
