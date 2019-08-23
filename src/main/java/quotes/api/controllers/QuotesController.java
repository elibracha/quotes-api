package quotes.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
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
	
    private TokenEndpoint tokenEndpoint;
    private TokenStore tokenStore;
	private QuoteFacade quoteFacade;

	public QuotesController(TokenEndpoint tokenEndpoint, TokenStore tokenStore,
			QuoteFacade quoteFacade) {
		this.tokenEndpoint = tokenEndpoint;
		this.tokenStore = tokenStore;
		this.quoteFacade = quoteFacade;
	}

	@GetMapping(value = "/quotes")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<List<Quote>> getQuotes() {
		return ResponseEntity.ok(quoteFacade.getQuotes());
	}

	@PostMapping(value = "/quotes")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<Object> postQuotes(@Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteFacade.postQuote(quote));
	}

	@PutMapping(value = "/quotes/{id}")
	@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
	public ResponseEntity<Object> updateQuotes(@PathVariable long id, @Valid @RequestBody Quote quote) {
		return ResponseEntity.ok(quoteFacade.updateQuote(id, quote));
	}

	@DeleteMapping(value = "/quotes/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
	public ResponseEntity<Object> deleteQuotes(@PathVariable Long id) {
		quoteFacade.deleteQuote(id);
		return ResponseEntity.ok().build();
	}

}
