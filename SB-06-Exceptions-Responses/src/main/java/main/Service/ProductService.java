package main.Service;


import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import main.DAO.ProductDAO;
import main.Model.Product;
import main.Repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductDAO pd;
	@Autowired
	private ProductRepository pr;
	
	public Product save(Product product) {
		return pd.save(product);
	}

	public List<Product> findAll() {
		return pr.findAll();
	}

	public Product findById(Integer pid) {
		return pd.findById(pid);
	}

	public Product update(Integer id, Product p) {
		Product prod = pd.findById(id);
		
		if(p.getName() != null)
			prod.setName(p.getName());
		if(p.getPrice() != null)
			prod.setPrice(p.getPrice());
		if(p.getDescription() != null)
			prod.setDescription(p.getName());
		if(p.getColor() != null)
			prod.setColor(p.getColor());

		return pd.save(prod);
	}

	public ResponseEntity<String> delete(Integer id) {
		Product p = pd.findById(id);
			pd.delete(p);
		return ResponseEntity.status(HttpStatus.OK).body("The Product with id " + p.getPid() + " is Deleted");
	}

	public Page<Product> page(Integer pageNumber) {
			Pageable p = PageRequest.of(pageNumber -1, 10);
		return pr.findAll(p);
	}

	public List<Product> sort(String param, String order) {
		
		if (order != null && order.equalsIgnoreCase("desc")) {
			return pr.findAll(Sort.by(param).descending());// sort in ascending order by default
		}
		return pr.findAll(Sort.by(param).ascending());
	}

	public List<Product> filter(ProductDAO pd) {
		Product p = new Product();
		BeanUtils.copyProperties(pd, p);		//Copies values from ProductDTO into product
		Example<Product> exe = Example.of(p);	//Creates a Query-by-Example
		
		return pr.findAll(exe);
	}

	public ResponseEntity<?> test(){
		int n = 2;
		if(n % 2 ==0) {
			return new ResponseEntity<Integer>(n,HttpStatus.OK);
		} else {
		return new ResponseEntity<String>("odd",HttpStatus.OK);
		}
	}
	
	public List<Product> priceRange(Double fPrice, Double tPrice) {
		return pr.getProductsPriceRange(fPrice, tPrice);
	}

	public List<Product> searchByName(String name) {
		return pr.findByNameContainingIgnoreCase(name);
	}
}
