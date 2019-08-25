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

import quotes.api.model.Quote;
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

	@GetMapping(value = "/quotes/{id}")
	public ResponseEntity<Quote> getQuote(@PathVariable long id) {
		return ResponseEntity.ok(quoteSerivce.getQuote(id));
	}

	@PostMapping(value = "/quotes")
	public ResponseEntity<Quote> postQuote(@Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteSerivce.postQuote(quote));
	}

	@PutMapping(value = "/quotes/{id}")
	public ResponseEntity<Quote> updateQuote(@PathVariable long id, @Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteSerivce.updateQuote(id, quote));
	}

	@DeleteMapping(value = "/quotes/{id}")
	public ResponseEntity<Object> deleteQuote(@PathVariable Long id) {
		quoteSerivce.deleteQuote(id);
		return ResponseEntity.ok().build();
	}
}
