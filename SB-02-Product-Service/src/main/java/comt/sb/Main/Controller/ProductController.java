package comt.sb.Main.Controller;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comt.sb.Main.model.Product;
import comt.sb.Main.repository.ProductRepository;

@RestController
@RequestMapping("/pd")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping("/save")
	public Product saveProduct(@RequestBody Product product){
		Product saved = productRepository.save(product);
		// saves the object and returns the saved object
		return saved;
	}
	
	// fetch ALL Products
	@GetMapping
	public Iterable<Product> fetchAll(){
		Iterable<Product> all = productRepository.findAll();
		return all;
	}
	
	// fetch By Id
	@GetMapping("/byID")
	public Product fetchById(@RequestParam Integer pid) {
		
//		Optional<Product> opt = productRepository.findById(pid);
//		//using 1st Way
//		if (opt.isPresent()) {
//			Product product = opt.get();
//			return product;
//		} else {
//			System.out.println("not found");
//			return null;
//		}
		
		//using 2nd way
//		Product product = opt.orElseThrow(() -> new RuntimeException("Product Not Found"));
//		return product;
		
		//using 3nd way
		return productRepository.findById(pid).orElseThrow(() -> new RuntimeException("Product not found"));
	}	
}
