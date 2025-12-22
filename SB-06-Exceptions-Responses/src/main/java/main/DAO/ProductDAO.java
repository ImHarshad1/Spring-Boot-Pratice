package main.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.Model.Product;
import main.Repository.ProductRepository;

@Repository
public class ProductDAO {

	@Autowired
	private ProductRepository pr;
	
	public Product save(Product product) {
		return pr.save(product);
	}

	public Product findById(Integer pid) {
		return pr.findById(pid).orElseThrow(() -> new RuntimeException("Product Not Found"));
	}

	public void delete(Product p) {
		 pr.delete(p);
	}

	
}