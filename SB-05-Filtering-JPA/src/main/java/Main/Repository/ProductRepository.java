package Main.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Main.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	// custom methods ==> No need of JPQL/HQL, but we need to follow method naming
	// convention -->
	// method Name should start from findBy (to add condition we have to use the
	// states of entity object with convention again)
// Name filters
	List<Product> findByNameStartsWithIgnoreCase(String prefix);
	List<Product> findByNameEndsWithIgnoreCase(String suffix);
	public List<Product> findByNameContainingIgnoreCase(String name);
	
// Price filters
	List<Product> findByPriceGreaterThan(Double price);
	List<Product> findByPriceGreaterThanEqual(Double price);
	List<Product> findByPriceLessThan(Double price);
	List<Product> findByPriceIsLessThanEqual(Double price);
	
// Color filters
	List<Product> findByColorIgnoreCase(String color);
	List<Product> findDistinctByColor(String color);
	
	// Combined filters
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

}
