package Main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Main.model.Product;
import Main.service.ProductService;

@RestController
@RequestMapping("/pd")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/saveAll")			 //Saving Product
	public List<Product> saved(@RequestBody List<Product> products) {
		return productService.saveAll(products);
	}
	
	@GetMapping			 		 	 //Fetch All Products
	public List<Product> fetchAll(){
		return productService.findAll();
	}
	
	@GetMapping("/id")	  			 //Fetch By Id only
	public Product fetchById(@RequestParam Integer pid) {
		return productService.getById(pid);
	}
	
	@PutMapping("/update/{id}")		//Updating Product
	public Product update(@PathVariable Integer id, @RequestBody Product product) {
		return productService.update(id, product);
	}
	
	@DeleteMapping("/delete/{id}")		//Deleting Product
	public String delete(@PathVariable Integer id) {
		return productService.deleteById(id);
	}
	
	@GetMapping("/page") 				//Pagination 
	public Page<Product> fetchPage(@RequestParam Integer pageNumber) {
//		if we want List Change return type from Page to List<>
		return productService.fetchByPage(pageNumber);
	}
	
	@GetMapping("/sort")				//Sorting
	public List<Product> sortRecords(@RequestParam(required = false, defaultValue = "price") String param,
									@RequestParam(required = false) String order){
				return productService.sortProducts(param, order);
	}
}
