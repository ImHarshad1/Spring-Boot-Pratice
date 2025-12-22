package main.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	// Custom queries ==> JPQL/HQL
	@Query("SELECT p FROM Product p WHERE p.price>=:fPrice AND p.price<=:tPrice")
	public List<Product> getProductsPriceRange(Double fPrice, Double tPrice);
	
	@Query("SELECT p FROM Product p WHERE p.name=:prodName AND p.price=:prodPrice")
	public List<Product> getByNameAndPrice(String prodName, Double prodPrice);

	// custom methods ==> No need of JPQL/HQL, but we need to follow method naming
	//for these methods,"method name must match entity fields"
	public List<Product> findByNameContainingIgnoreCase(String name);
	
	public List<Product> findByNameAndPrice(String name, Double price);
	
	public List<Product> findByPriceBetween(Double fromPrice, Double toPrice);
	
	public List<Product> findByPriceLessThan(Double price);
}
