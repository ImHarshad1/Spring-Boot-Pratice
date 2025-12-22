package main.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.DAO.ProductDAO;
import main.Model.Product;
import main.Repository.ProductRepository;
import main.Service.ProductService;

@RestController
@RequestMapping("/pd")
public class ProductController {
	
	@Autowired
	private ProductService ps;
	@Autowired
	private ProductRepository pr;
	
	@PostMapping("/save")		//using constructors of ResponseEntity
	public ResponseEntity<Product> saved(@RequestBody Product product) {
		Product p = ps.save(product);
		ResponseEntity<Product> res = new ResponseEntity<Product>(p, HttpStatus.CREATED);
		return res;
	}
//	@PostMapping("/save")		//using Static Methods
//	public ResponseEntity<Product> saved1(@RequestBody Product p){
//		return ResponseEntity.status(HttpStatus.CREATED.body(ps.saved1(p)));
//	}   status(HttpStatus.CREATED) bcoz, the standard HTTP status code is 201 Created
	
	@GetMapping()
		public List<Product> fetchAll(){
			return ps.findAll();
	}

	@GetMapping("byId")
	public ResponseEntity<Product> findByID(@RequestParam Integer pid){
		return ResponseEntity.ok(ps.findById(pid));   //OK() bcoz,the standard response is 200 OK
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Product p){
		Product prod = ps.update(id, p);			//use body(), to print message in output
		return ResponseEntity.ok().body("The Product with id " + prod.getPid() + " is updated");
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		return ResponseEntity.noContent().build();   //noContent() for bcoz, the standard response is 204 No Content.
	}
	
	@GetMapping("/page")  //sort, filter, range, search 
	public Page<Product> page(@RequestParam Integer pageNumber){
		return ps.page(pageNumber);
	}
	
	@GetMapping("/sort")
	public List<Product> sorting(@RequestParam(required = false, defaultValue = "price") String param, @RequestParam(required = false) String order){
		return ps.sort(param,order);
	}
	
	@GetMapping("/filter")
	public List<Product> filter(@RequestBody ProductDAO pd){
		return ps.filter(pd);
	}
	
	@GetMapping("/range")
	public List<Product> range(@RequestParam Double fPrice, @RequestParam Double tPrice){
		return ps.priceRange(fPrice,tPrice);
	}
	
	@GetMapping("/search")
	public List<Product> searchByName(@RequestParam String name){
		return ps.searchByName(name);
	}
	
	@GetMapping("/exc")
	public String msg() {
		String s = null;
		
		s.charAt(10);

		int a = 10 / 0;
		
		return "Exceptions";
	}
	
	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<String> handleArithmetic(ArithmeticException exception){
		System.out.println("handled AE");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointer(NullPointerException exception){
		System.out.println("Handled Null Pointer Exception");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllException(Exception exception){
		System.out.println("Handled All Type of Exceptions");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@GetMapping("/byNameAndPrice")
	public List<Product> getByNameAndPrice(@RequestParam String name, @RequestParam Double price) {
	    return pr.getByNameAndPrice(name, price);
	}

}
