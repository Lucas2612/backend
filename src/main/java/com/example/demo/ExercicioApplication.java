package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.repository.ItemRepo;

@SpringBootApplication
public class ExercicioApplication {

	@Autowired
	private ItemRepo itemRepo;

	public static void main(String[] args) {
		SpringApplication.run(ExercicioApplication.class, args);
		
		
	}
	
	/*
	 * @Override public void run(String... args) throws Exception {
	 * 
	 * itemRepo.deleteAll();
	 * 
	 * // save a couple of customers itemRepo.save(new Item(5L, "item 1", "12.5"));
	 * 
	 * itemRepo.save(new Item(10L, "item 2", "5.5")); itemRepo.save(new Item(15L,
	 * "item 3", "1.8")); }
	 */

	/*
	 * @Override 
	 * public void run(String... args) throws Exception {
	 * 
	 * itemRepo.deleteAll();
	 * 
	 * // save a couple of customers itemRepo.save(new Item(5, "item 1", 12.5));
	 * itemRepo.save(new Item(10, "item 2", 5.5)); itemRepo.save(new Item(15,
	 * "item 3", 1.8));
	 * 
	 * 
	 * repository.save(new Customer("Bob", "Smith"));
	 * 
	 * // fetch all customers System.out.println("Customers found with findAll():");
	 * System.out.println("-------------------------------");
	 * 
	 * for (Item item : itemRepo.findAll()) { System.out.println(item); }
	 * 
	 * System.out.println();
	 * 
	 * // fetch an individual customer
	 * System.out.println("Customer found with findByFirstName('Alice'):");
	 * System.out.println("--------------------------------");
	 * System.out.println(repository.findByFirstName("Alice"));
	 * 
	 * System.out.println("Customers found with findByLastName('Smith'):");
	 * System.out.println("--------------------------------"); for (Customer
	 * customer : repository.findByLastName("Smith")) {
	 * System.out.println(customer); }
	 * 
	 * 
	 * }
	 */

}
