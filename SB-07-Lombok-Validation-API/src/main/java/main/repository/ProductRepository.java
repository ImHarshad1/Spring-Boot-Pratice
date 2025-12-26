package main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import main.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	// custom methods ==> No need of JPQL/HQL, but we need to follow method naming
	// method Name should start from findBy & then states of entity object
	// Name filters
	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByNameStartsWithIgnoreCase(String prefix);
	List<Product> findByNameEndsWithIgnoreCase(String suffix);
		
	// Price filters
	List<Product> findByPriceGreaterThan(Double price);
	List<Product> findByPriceGreaterThanEqual(Double price);
	List<Product> findByPriceLessThan(Double price);
	List<Product> findByPriceIsLessThanEqual(Double price);
		
	// Color filters
	List<Product> findByColorIgnoreCase(String color);
	List<Product> findDistinctByColor(String color);
		
	// Combined filters
	List<Product> findByNameAndPriceAndColor(String name, Double Price, String color);
	List<Product> findByColorOrPrice(String color, Double price);
	List<Product> findByNameAndPrice(String name, Double price);
	// Spring maps these parameters to Product entity fields; names in the method (Name, Price) must match entity fields
		
	// Ordering
	List<Product> findByPriceBetween(Double fromPrice, Double toPrice);
	List<Product> findByOrderByPriceAsc();
	List<Product> findByOrderByNameDesc();

	// Top/First
	List<Product> findTop5ByOrderByPriceDesc();
	Product findFirstByName(String name);

	// Utility
	boolean existsByName(String name);
	long countByColor(String color);
	
	//no need to create one REST endpoint per repository method. we already exposed them in BUSINESS APIs
}
/*--Q. Why not create API for every repository method?
->I don’t expose every repository method as an API because that would clutter the controller and tightly couple the API to the database layer. 
Instead, I design clean, resource-oriented endpoints like /search, /filter, /range etc that accept flexible parameters.
Inside the service layer, I apply the business logic — for example, validating inputs, combining multiple filters, or handling defaults — and then call the appropriate repository methods. 
This way, the repository stays focused on data access, while the service layer enforces business rules, and the API surface remains small, intuitive, and easy to maintain
*/

