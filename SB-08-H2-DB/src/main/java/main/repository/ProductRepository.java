package main.repository;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import main.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	
}