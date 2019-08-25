package quotes.api.handlers;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import quotes.api.exceptions.MissMatchItemNameException;
import quotes.api.model.QuoteError;
import quotes.api.model.enums.StatusCode;

@RestControllerAdvice
public class QuotesExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(new QuoteError(StatusCode.ERROR.name(),
				ex.getBindingResult().getFieldError().getDefaultMessage(), StatusCode.ERROR.ordinal()), status);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class, MissMatchItemNameException.class,
			NoSuchElementException.class })
	public ResponseEntity<QuoteError> handleError(Exception e) {
		String message;
		
		switch (e.getClass().getSimpleName()) {
			case "MissMatchItemNameException":
				message = "item id exist but name value is not the same.";
				break;
			case "NoSuchElementException":
				message = "could not find element.";
				break;
			default:
				message = "name is not unique.";
		}

		return new ResponseEntity<QuoteError>(new QuoteError(StatusCode.ERROR.name(),
					message, StatusCode.ERROR.ordinal()),
					HttpStatus.BAD_REQUEST);
	}

}
