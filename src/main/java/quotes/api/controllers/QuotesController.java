package quotes.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quotes.api.facades.interfaces.QuoteFacade;
import quotes.api.model.Quote;

@RestController
@RequestMapping(value = "/api/v1")
public class QuotesController {

	private QuoteFacade quoteFacade;

	public QuotesController(QuoteFacade quoteFacade) {
		this.quoteFacade = quoteFacade;
	}

	@GetMapping(value = "/quotes")
	public ResponseEntity<List<Quote>> getQuotes() {
		return ResponseEntity.ok(quoteFacade.getQuotes());
	}

	@PostMapping(value = "/quotes")
	public ResponseEntity<Object> postQuotes(@Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteFacade.postQuote(quote));
	}

	@PutMapping(value = "/quotes/{id}")
	public ResponseEntity<Object> updateQuotes(@PathVariable long id, @Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteFacade.updateQuote(id, quote));
	}

	@DeleteMapping(value = "/quotes/{id}")
	public ResponseEntity<Object> deleteQuotes(@PathVariable Long id) {
		quoteFacade.deleteQuote(id);
		return ResponseEntity.ok().build();
	}

}
