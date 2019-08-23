package quotes.api.facades;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import quotes.api.daos.QuoteDAO;
import quotes.api.facades.interfaces.QuoteFacade;
import quotes.api.model.Quote;

@Service
public class QuoteFacadeImpl implements QuoteFacade {
	private QuoteDAO quoteService;

	public QuoteFacadeImpl(QuoteDAO quoteService) {
		this.quoteService = quoteService;
	}

	public List<Quote> getQuotes() {
		return quoteService.getQuotes();
	}

	public Object postQuote(Quote quote) throws RuntimeException {
		return quoteService.postQuote(quote);
	}

	public Object updateQuote(long id, Quote quote) {
		return quoteService.updateQuote(id, quote);
	}

	public void deleteQuote(@PathVariable Long id) {
		quoteService.deleteQuote(id);
	}
}
