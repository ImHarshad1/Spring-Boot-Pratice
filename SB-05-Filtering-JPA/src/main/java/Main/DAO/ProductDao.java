package Main.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Main.Model.Product;
import Main.Repository.ProductRepository;
@Repository
public class ProductDao {

	@Autowired
	private ProductRepository pr;
	
	public Product save(Product product) {
		
		return pr.save(product);
	}

	public Product findById(Integer pid) {
		return pr.findById(pid).orElseThrow(() -> new RuntimeException("Product Not Found"));
	}

	public void delete(Product pd) {
		pr.delete(pd);
	}
}
