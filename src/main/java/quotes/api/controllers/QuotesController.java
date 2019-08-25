package quotes.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quotes.api.exceptions.MissMatchItemNameException;
import quotes.api.model.Quote;
import quotes.api.model.QuoteError;
import quotes.api.model.enums.StatusCode;
import quotes.api.services.QuoteService;

@RestController
@RequestMapping(value = "/api/v1")
public class QuotesController {

	private QuoteService quoteSerivce;

	public QuotesController(QuoteService quoteFacade) {
		this.quoteSerivce = quoteFacade;
	}

	@GetMapping(value = "/quotes")
	public ResponseEntity<List<Quote>> getQuotes() {
		return ResponseEntity.ok(quoteSerivce.getQuotes());
	}

	@PostMapping(value = "/quotes")
	public ResponseEntity<Quote> postQuotes(@Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteSerivce.postQuote(quote));
	}

	@PutMapping(value = "/quotes/{id}")
	public ResponseEntity<Quote> updateQuotes(@PathVariable long id, @Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteSerivce.updateQuote(id, quote));
	}

	@DeleteMapping(value = "/quotes/{id}")
	public ResponseEntity<Object> deleteQuotes(@PathVariable Long id) {
		quoteSerivce.deleteQuote(id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(value = {
			DataIntegrityViolationException.class,
			MissMatchItemNameException.class
	})
	public QuoteError handleError(Exception e) {
		if (e instanceof DataIntegrityViolationException)
			return new QuoteError(StatusCode.ERROR.name(), "name is not unique.", StatusCode.ERROR.ordinal());
		else 
			return new QuoteError(StatusCode.ERROR.name(), "item id exist but name value is not the same.",
					StatusCode.ERROR.ordinal());
	}
}
