 package main.controller;

import java.util.List;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.DTO.ProductDTO;
import main.model.Product;
import main.service.ProductService;

@RestController
@RequestMapping("/pd")
public class ProductController {

	@Autowired
	private ProductService ps;
	
	@PostMapping("/save")
	public ResponseEntity<Product> savedProd(@RequestBody Product product){
		Product p = ps.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(p);
	}
	
	@GetMapping("/fetchAll")
	public List<Product> fetchAll(){
		return ps.findAll();
	}
	
	@GetMapping("/byId")
	public ResponseEntity<Product> findById(@RequestParam Integer pid){
		return ResponseEntity.ok(ps.findById(pid));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product product){
		Product p = ps.update(id, product);
		return ResponseEntity.ok().body("The Product with id " + p.getPid() + " is Updated");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		 ps.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/page")
	public Page<Product> page(@RequestParam Integer pageNumber){
		return ps.page(pageNumber);
		
	}
	
	@GetMapping("/sort")
	public List<Product> sort(@RequestParam(required = false, defaultValue = "price" ) String param, 
							@RequestParam(required = false) String order) {
		return ps.sort(param,order);
	}
	
	@GetMapping("/filter")
	public List<Product> filter(@RequestParam ProductDTO pd){
		return ps.filter(pd);
	}

	@GetMapping("/range")
	public List<Product> range(@RequestParam Double fPrice, @RequestParam Double tPrice){
		return ps.range(fPrice, tPrice);
	}
	
	@GetMapping("/search")
	public List<Product> search(@RequestParam String name){
		return ps.search(name);
	}
}