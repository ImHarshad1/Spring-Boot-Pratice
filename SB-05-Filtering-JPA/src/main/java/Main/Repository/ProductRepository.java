package Main.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Main.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	// Custom queries ==> JPQL/HQL
	// @Query("SELECT p FROM Product p WHERE p.price>=?1 AND p.price<=?2")       // Index
	// based parameter
//	@Query(value = "SELECT * from products", nativeQuery = true)		    	//SQL query

	@Query("SELECT p FROM Product p WHERE p.price>=:fPrice AND p.price<=:tPrice")
	List<Product> PriceRange(Double fPrice, Double tPrice);

	@Query("SELECT p FROM Product p WHERE p.name=:prodName AND p.price=:prodPrice")
	public List<Product> getByNameAndPrice(String prodName, Double prodPrice);
	
	// custom methods ==> No need of JPQL/HQL, but we need to follow method naming
	// convention -->
	// method Name should start from findBy (to add condition we have to use the
	// states of entity object with convention again)
	public List<Product> findByNameContainingIgnoreCase(String name);
	
	public List<Product> findByNameAndPrice(String name, Double price);
	// Spring maps these parameters to Product entity fields; names in the method (Name, Price) must match entity fields

	public List<Product> findByPriceBetween(Double fromPrice, Double toPrice);
	
	public List<Product> findByPriceIsLessThanEqual(Double price);

}
