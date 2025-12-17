package Main.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Main.model.Product;
import Main.repository.ProductRepository;

@Repository
public class ProductDao {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> saveAll(List<Product> products) {
		return productRepository.saveAll(products);
	}

	public Product getById(Integer pid) {
		return productRepository.findById(pid).orElseThrow(() -> new RuntimeException("Product Not Found"));
	}

	public void delete(Product db) {
		productRepository.delete(db);
	}

	public Product save(Product dbProd) {
		return productRepository.save(dbProd);
	}
}
