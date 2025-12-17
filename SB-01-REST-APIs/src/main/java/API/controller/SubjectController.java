package API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubjectController {
	@PostMapping("/save")
	public String saveSubject() {
		System.out.println("Subject saved");
		return "Subject saved";
	}

	@GetMapping("/get")
	public String getSubject() {
		System.out.println("Subject fetched");
		return "Subject fetched";
	}
}