package main.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import main.model.Product;
import main.repository.ProductRepository;

@Component 			// Marks this class as a Spring Bean so Spring Boot can detect and run it
public class DataLoader implements CommandLineRunner{ 
//	CommandLineRunner → Special Spring Boot interface; its run() method executes automatically after the app starts.
	
	private final ProductRepository productRepository; 
	
	// Constructor-based dependency injection (Spring will automatically provide ProductRepository)
	public DataLoader(ProductRepository productRepository) { 
		this.productRepository = productRepository;
	}
	
	/**
     * The run() method is executed automatically at application startup
     * because this class implements CommandLineRunner.
     * 
     * CommandLineRunner is a Spring Boot interface that allows you to run
     * custom code immediately after the application context is initialized.
     * 
     * In this case, we use it to:
     * 1. Create sample Product objects.
     * 2. Save them into the H2 in-memory database using ProductRepository.
     * 3. Fetch all products from the DB and print them to the console.
     */
	@Override public void run(String... args) throws Exception {
		// Create objects
		Product p1 = new Product(null, "Laptop", 55000.0, "Gaming laptop", "Black", null, null); 
		Product p2 = new Product(); 
		p2.setName("Smartphone"); 
		p2.setPrice(25000.0); 
		p2.setDescription("Android phone"); 
		p2.setColor("Blue");
		
		// Save objects to H2 DB
		productRepository.save(p1); 
		productRepository.save(p2);
		
		// Fetch all products from DB and print them to console 
		// System.out::println calls Product.toString() for readable output
		productRepository.findAll().forEach(System.out::println); 
		
//Below is shorter inline version
		// Insert sample data 
//		productRepository.save(new Product(null, "Laptop", 55000.0, "Gaming laptop", "Black", null, null)); 
//		productRepository.save(new Product(null, "Smartphone", 25000.0, "Android phone", "Blue", null, null));

//		// Print all data in console 
//		productRepository.findAll().forEach(System.out::println);
	}

}
