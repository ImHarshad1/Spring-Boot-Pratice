package main.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import main.DTO.ProductDTO;
import main.Exception.ProductNotFoundException;
import main.model.Product;
import main.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository pr;

	public Product save(Product product) {
		return pr.save(product);
	}

	public List<Product> findAll() {
		return pr.findAll();
	}

	public Product findById(Integer pid) {
		return pr.findById(pid).orElseThrow(() -> new RuntimeException("Product Not Found"));
	}

	public Product update(Integer id, Product prod) {
		Product p = pr.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
		
		if(prod != null)
			p.setName(prod.getName());
		if(prod != null)
			p.setPrice(prod.getPrice());
		if(prod != null)
			p.setDescription(prod.getDescription());
		if(prod != null)
			p.setPrice(prod.getPrice());
		
		return pr.save(p);
	}
	
	public void delete(Integer id) {
		Product p =  pr.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
			pr.delete(p);
	}

	public Page<Product> page(Integer pageNumber) {
		Pageable p = PageRequest.of(pageNumber -1, 10);
		return pr.findAll(p);
	}

	public List<Product> sort(String param, String order) { //3
		if(order != null && order.equalsIgnoreCase("desc")) {
			return pr.findAll(Sort.by(param).descending());
		}
		return pr.findAll(Sort.by(param).ascending());
	}

	public List<Product> filter(ProductDTO pd) { //4
		Product p = new Product();
		BeanUtils.copyProperties(pd, p);		//Copies values from ProductDTO into product
		Example<Product> ex = Example.of(p);	//Creates a Query-by-Example
		return pr.findAll(ex);
	}

	public List<Product> range(Double fPrice, Double tPrice) {
		return pr.findByPriceBetween(fPrice, tPrice);
	}

	public List<Product> search(String name) {
		return pr.findByNameContainingIgnoreCase(name);
	}

}
