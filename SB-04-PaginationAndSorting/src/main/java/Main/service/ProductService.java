package Main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Main.dao.ProductDao;
import Main.model.Product;
import Main.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductRepository productRepository;

	public List<Product> saveAll(List<Product> products) {
		return productDao.saveAll(products);
	}

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product getById(Integer pid) {
		return productDao.getById(pid);
	}

	public Product update(Integer id, Product product) {
		Product dbProd = productDao.getById(id);
		
		if(product.getName() != null)
			dbProd.setName(product.getName());
		if(product.getPrice() != null)
			dbProd.setPrice(product.getPrice());
		if(product.getDescription() != null)
			dbProd.setDescription(product.getDescription());
		if(product.getColor() != null)
			dbProd.setColor(product.getColor());
		
		return productDao.save(dbProd);
	}

	public String deleteById(Integer id) {
		Product db = productDao.getById(id);
		productDao.delete(db);
		return "Deleted";
	}

	public Page<Product> fetchByPage(Integer pageNumber) {
		// logic to fetch records based on pageNumber 
		//will also get info like totalElements, totalPages
		Pageable pageable = PageRequest.of(pageNumber -1, 10);
		
		Page<Product> all =productRepository.findAll(pageable);
		List<Product> pd = all.toList(); 
		return all;
	}
	 //if we want List and not other details use this
//	public List<Product> fetchByPage(Integer pageNumber) {
//	    Pageable pageable = PageRequest.of(pageNumber - 1, 10);
//	    Page<Product> page = productRepository.findAll(pageable);
//	    return page.toList();   // ✅ Only list returned
//	} 

	// Sorting order -> asc,desc
	public List<Product> sortProducts(String param, String order) {
		if(order != null && order.equalsIgnoreCase("desc")) {
			return productRepository.findAll(Sort.by(param).descending());
		}
		return productRepository.findAll(Sort.by(param).ascending());
	}
}