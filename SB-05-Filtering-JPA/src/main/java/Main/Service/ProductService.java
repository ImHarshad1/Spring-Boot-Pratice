package Main.Service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Main.DAO.ProductDao;
import Main.DTO.ProductDTO;
import Main.Model.Product;
import Main.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao dao;
	
	@Autowired
	private ProductRepository pr;
	
	public Product save(Product product) {
		return dao.save(product);
	}

	public List<Product> findAll() {
		return pr.findAll();
	}

	public Product findById(Integer pid) {
		return dao.findById(pid);
	}

	public Product update(Integer id, Product product) {
		Product p = dao.findById(id);
		
		if(product.getName() != null)
			p.setName(product.getName());
		if(product.getPrice() != null)
			p.setPrice(product.getPrice());
		if(product.getDescription() != null)
			p.setDescription(product.getDescription());
		if(product.getColor() !=  null)
			p.setColor(product.getColor());
		
		return dao.save(p);
	}

	public String deleteById(Integer id) {
		Product pd = dao.getById(id);
		dao.delete(pd);
		return "Deleted";
	}

	public Page<Product> fetchByPage(Integer pageNumber) {
		Pageable pg =PageRequest.of(pageNumber -1, 10);
		return pr.findAll(pg);
	}

	public List<Product> sort(String param, String order) {
		if(order != null && order.equalsIgnoreCase("desc")) {
		return pr.findAll(Sort.by(param).descending());
		}
		return pr.findAll(Sort.by(param).ascending());
	}

	public List<Product> filter(ProductDTO pd) {
		Product p = new Product();
		
		BeanUtils.copyProperties(pd, p);
		
		Example<Product> ex = Example.of(p);
		
		return pr.findAll(ex);
	}

	public List<Product> range(Double fPrice, Double tPrice) {
		return pr.PriceRange(fPrice, tPrice);
	}

	public List<Product> search(String name) {
		return pr.findByNameContainingIgnoreCase(name);
	}
}