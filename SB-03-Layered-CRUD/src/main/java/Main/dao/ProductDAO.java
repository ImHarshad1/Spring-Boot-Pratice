package Main.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Main.model.Product;
import Main.repository.ProductRepository;

//DAO is responsible for how data is fetched
@Repository
public class ProductDAO {
	
	@Autowired
	public ProductRepository productRepository;

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Product getById(Integer pid) {
		return productRepository.findById(pid).orElseThrow(() -> new RuntimeException("Product Not Found"));
	}

	public void delete(Product pd) {
		productRepository.delete(pd);
	}

}
