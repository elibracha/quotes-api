package quotes.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import quotes.api.model.Quote;
import quotes.api.services.QuoteService;

@RestController
@RequestMapping(value = "/api/v1")
public class QuotesController {

	private QuoteService quoteService;

	public QuotesController(QuoteService quoteService) {
		super();
		this.quoteService = quoteService;
	}

	@GetMapping(value = "/quotes")
	public ResponseEntity<List<Quote>> getQuotes() {
		return quoteService.getQuotes();
	}

	@PostMapping(value = "/quotes")
	public ResponseEntity<Object> postQuotes(@Valid @RequestBody Quote quote) {
		return quoteService.postQuotes(quote);
	}

	@PutMapping(value = "/quotes/{id}")
	public ResponseEntity<Object> updateQuotes(@PathVariable long id, @Valid @RequestBody Quote quote) {
		return quoteService.updateQuotes(id, quote);
	}

	@DeleteMapping(value = "/quotes/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteQuotes(@PathVariable Long id) {
		quoteService.deleteQuotes(id);
	}

}
