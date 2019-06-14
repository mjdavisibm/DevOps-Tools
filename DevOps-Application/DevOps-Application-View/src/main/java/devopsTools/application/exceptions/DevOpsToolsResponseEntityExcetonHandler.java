package devopsTools.application.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class DevOpsToolsResponseEntityExcetonHandler extends ResponseEntityExceptionHandler {

  
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException
	(CustomerNotFoundException ex, WebRequest request) throws Exception {

		DevOpsToolsExceptionReponse exR = new DevOpsToolsExceptionReponse(
				new Date(),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(exR,HttpStatus.NOT_FOUND);
	}	
	
}
