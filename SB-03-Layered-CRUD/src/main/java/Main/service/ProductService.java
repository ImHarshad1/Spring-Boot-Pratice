package Main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Main.dao.ProductDAO;
import Main.model.Product;
import Main.repository.ProductRepository;

//Business logic of an application
//Service decides what to do with data
@Service
public class ProductService {
	
	@Autowired
	public ProductDAO produDao;
	
	@Autowired
	public ProductRepository productRepository;

	//saving product
	public Product save(Product product) {
		return produDao.save(product);
	} 

	// fetch all products
	public List<Product> findAll() {
		return productRepository.findAll();  
//		findAll() is called directly from the repository because no additional DAO logic is required
	}
	
//	// Service
//	public List<Product> findAll() {
//	    return produDao.findAll();
//	}
//
//	//This should be in DAO class
//	public List<Product> findAll() {
//	    return productRepository.findAll();
//	}

	//fetch by ID
	public Product findById(Integer pid) {
		return produDao.getById(pid);
	}

	//Update
	public Product update(Integer pid, Product product) { 
		//product comes from request body, It is not managed by Hibernate
		
		//pd is fetched from database, It is a managed entity
		Product pd = produDao.getById(pid);
		
		if(product.getName() != null)
			pd.setName(product.getName());
		if(product.getPrice() != null)
			pd.setPrice(product.getPrice());
		if(product.getDescription() != null)
			pd.setDescription(product.getDescription());
		if(product.getColor() !=null )
			pd.setColor(product.getColor());
		return produDao.save(pd);
	}

	//Delete
	public String deleteById(Integer pid) {
		Product pd = produDao.getById(pid); //pd is a managed entity, Hibernate knows its ID
		produDao.delete(pd);
		return "Deleted";
	}
}
