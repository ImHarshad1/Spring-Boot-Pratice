package main.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

	private String name;
	private String email;
	private String password;
}
