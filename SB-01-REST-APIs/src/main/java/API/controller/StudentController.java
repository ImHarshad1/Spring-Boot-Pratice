package API.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class StudentController {
	
	@PostMapping("/save")
	public String student() {
		System.out.println("student saved");
		return "Saved Successfully";
	}
	
	@GetMapping("/get")
	public String getStudnet() {
		System.out.println("Student fetched");
		return "Student fetched successfully";
	}
	
	@DeleteMapping("/delete")
	public String dltStudent() {
		System.out.println("Deleted");
		return "student deleted";
	}
	
	@PutMapping("/update")
	public String updateStudent() {
		System.out.println("Updated");
		return "student updated";
	}
}