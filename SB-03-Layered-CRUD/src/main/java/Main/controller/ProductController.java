package Main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import Main.service.ProductService;
import Main.model.Product;

@RestController
@RequestMapping("/pd")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	// saving product
	@PostMapping("/save")
	public Product saveProduct(@RequestBody Product product) {
		Product saved = productService.save(product);// saves the object and returns the saved object
		return saved;
	}
	
	// fetch all products
	@GetMapping
	public List<Product> fetchAll() {
		List<Product> products = productService.findAll();
		return products;
	}
	
	//fetch by ID
	@GetMapping("/id")
	public Product fetchById(@RequestParam Integer id) {
		return productService.findById(id);
	
	}
	
	//Update
	@PutMapping("/update/{id}")
	public Product update(@PathVariable Integer id, @RequestBody Product product) {
		return productService.update(id, product);
	}
	
	//Delete
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable Integer id) {
		return productService.deleteById(id);
	}
}