package main.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(ArithmeticException.class)
	public ResponseEntity<String> handleException(ArithmeticException exception){
		System.out.println("Handled Arithmatic Exception Globally");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());	
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointer(NullPointerException exception){
		System.out.println("Handled Null Pointer Exception Globally");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleAllExceptions(Exception exception){
		System.out.println("Handled All Type of Exceptions Globally");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}
