package Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Main.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
