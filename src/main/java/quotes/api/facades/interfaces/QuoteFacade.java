package quotes.api.facades.interfaces;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import quotes.api.model.Quote;

public interface QuoteFacade {
	List<Quote> getQuotes();

	Object postQuote(Quote quote);

	Object updateQuote(long id, Quote quote);

	void deleteQuote(@PathVariable Long id);
}
