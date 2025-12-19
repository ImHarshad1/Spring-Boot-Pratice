package Main.Controller;

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

import Main.DTO.ProductDTO;
import Main.Model.Product;
import Main.Service.ProductService;

@RestController
@RequestMapping("pd")
public class ProductController {

	@Autowired
	private ProductService ps;
	
	@PostMapping("/save")
	public Product saveProd(@RequestBody Product product) {
		return ps.save(product);
	}
	
	@GetMapping()
	public List<Product> fetchAll(){
		return ps.findAll();
	}
	
	@GetMapping("/ById")
	public Product fetchById(@RequestParam Integer pid) {
		return ps.findById(pid);
	}
	
	@PutMapping("/update/{id}")
	public Product update(@PathVariable Integer id, @RequestBody Product product) {
		return ps.update(id,product);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		return ps.deleteById(id);
	}
	
	@GetMapping("/page")
	public Page<Product> paging(@PathVariable Integer pageNumber){
		return ps.fetchByPage(pageNumber);
	}
	
	@GetMapping("/sort")
	public List<Product> sorting(@RequestParam(required = false, defaultValue="price") String param, @RequestParam(required = false) String order){
		return ps.sort(param, order);
	}
	
	@GetMapping("/filter")
	public List<Product> filtering(@RequestBody ProductDTO pd){
		return ps.filter(pd);	
	}
	
	@GetMapping("/range")
	public List<Product> PriceRange(@RequestParam Double fPrice, @RequestParam Double tPrice){
		return ps.range(fPrice, tPrice);
	}
	
	@GetMapping("/search")
	public List<Product> searching(@RequestParam String name){
		return ps.search(name);
	}
	
}
