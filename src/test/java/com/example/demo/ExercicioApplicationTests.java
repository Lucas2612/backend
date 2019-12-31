package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
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
	
	private Item item1;
	private Item item2;
	private Item item3;
	private Item item4;
	private Item item5;
	
	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	private Usuario usuario4;
	
	ItemCart itemCart1;
	ItemCart itemCart2;
	ItemCart itemCart3;
	ItemCart itemCart2_1;
	ItemCart itemCart2_2;
	ItemCart itemCart2_3;
	Cart cart1;
	Cart cart2;


	@Test
	@Order(1)
	public void testItems() {

		load();

		// count
		List<Item> items = itemController.findAll();
		assertEquals(items.size(), 5);
		assertEquals(item1, itemController.getItem(1L));

		itemController.deleteItem(1L);
		assertNull(itemController.getItem(1L));
		items = itemController.findAll();
		assertEquals(items.size(), 4);

	}

	@Test
	@Order(2)
	public void testUsuarios() {

		load();

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
		
		load();


		assertEquals(3, cartController.getCart(2).getItemCarts().size());

		// delete item remove from the cart
		itemController.deleteItem(2L);
		assertEquals(2, cartController.getCart(2).getItemCarts().size());

		// delete usuario delete cart
		usuarioController.deleteUsuario(2L);
		assertNull(cartController.getCart(2));

		assertEquals(1, cartController.findAll().size());

	}

	@Test
	@Order(4)
	public void testCompra() {
		
		load();

		List<Cart> listCart = cartController.findAll();
		assertEquals(2, listCart.size());
		assertEquals(3, cartController.getCart(3).getItemCarts().size());

		List<Compra> compras = compraController.getCompras(3L);
		assertNotNull(compras);
		assertEquals(3, compras.size());
		
		double total = 0.0;
		for (Compra compra : compras) {
			total += compra.getValorTotal();
		}
		
		assertEquals(180, total, 0.0);
	}

	@Test
	@Order(5)
	public void testGrupo() {
		load();
		
		List<Cart> listCart = cartController.findAll();
		assertEquals(2, listCart.size());


		List<Grupo> grupos = grupoController.getGrupos();
		assertNotNull(grupos);
		assertEquals(2, grupos.size());

		itemController.deleteItem(3L); 
		assertEquals(2, grupos.size());
		
		// test total
		double total = 0.0;
		for (Grupo grupo : grupos) {
			total += grupo.getValorTotal();
		}
		
		assertEquals(245, total, 0.0);
		
	}
	
	
	public void load(){
		mongoTemplate.dropCollection("item");
		mongoTemplate.dropCollection("usuario");
		mongoTemplate.dropCollection("cart");

		item1 = new Item(1L, "Coffee", new Double(5));
		item2 = new Item(2L, "Water", new Double(10));
		item3 = new Item(3L, "Rice", new Double(15));
		item4 = new Item(4L, "Bean", new Double(20));
		item5 = new Item(5L, "Potato", new Double(25));

		// inserting items
		itemController.addItem(item1);
		itemController.addItem(item2);
		itemController.addItem(item3);
		itemController.addItem(item4);
		itemController.addItem(item5);

		usuario1 = new Usuario(1L, "John", "john@test.com");
		usuario2 = new Usuario(2L, "Mary", "mary@test.com");
		usuario3 = new Usuario(3L, "Bill", "bill@test.com");
		usuario4 = new Usuario(4L, "Morgan", "morgan@test.com");

		// inserting usuarios
		usuarioController.addUsuario(usuario1);
		usuarioController.addUsuario(usuario2);
		usuarioController.addUsuario(usuario3);
		usuarioController.addUsuario(usuario4);

		itemCart1 = new ItemCart(item2, 3);
		itemCart2 = new ItemCart(item3, 1);
		itemCart3 = new ItemCart(item4, 1);
		List<ItemCart> itemCarts = new ArrayList<ItemCart>();
		itemCarts.add(itemCart1);
		itemCarts.add(itemCart2);
		itemCarts.add(itemCart3);
		cart1 = new Cart(2L, itemCarts);

		itemCart2_1 = new ItemCart(item2, 1);
		itemCart2_2 = new ItemCart(item3, 10);
		itemCart2_3 = new ItemCart(item4, 1);
		List<ItemCart> itemCarts2 = new ArrayList<ItemCart>();
		itemCarts2.add(itemCart2_1);
		itemCarts2.add(itemCart2_2);
		itemCarts2.add(itemCart2_3);
		cart2 = new Cart(3L, itemCarts2);

		cartController.addCart(cart1);
		cartController.addCart(cart2);
		
	}

}
