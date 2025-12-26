package main.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDTO {
	
	private String name;
	
	private Double price;
	
	private String color;

}
