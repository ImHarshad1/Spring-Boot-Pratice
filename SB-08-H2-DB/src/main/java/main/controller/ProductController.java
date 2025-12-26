package main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.model.Product;
import main.repository.ProductRepository;

@RestController
@RequestMapping("/pd")
public class ProductController {

	@Autowired
	private ProductRepository pr;
	
	@PostMapping("/save")
	public ResponseEntity<Product> save(@RequestBody Product product){
		System.out.println("Save Operation Triggered");
		return ResponseEntity.status(HttpStatus.CREATED).body(pr.save(product));
	}
	
	@GetMapping()
	public ResponseEntity<List<Product>> fetchAll(){
		return ResponseEntity.status(HttpStatus.OK).body(pr.findAll());
	}
	
	@GetMapping("/byId")
	public ResponseEntity<Product> findById(@RequestParam Integer pid){
		return ResponseEntity.status(HttpStatus.OK).body(pr.findById(pid).orElseThrow(() -> new RuntimeException("Product Not found")));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id){
		pr.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
	}

}
