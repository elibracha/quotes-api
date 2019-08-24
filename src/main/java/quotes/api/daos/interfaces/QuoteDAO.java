package quotes.api.daos.interfaces;

import java.util.List;

import quotes.api.model.Quote;

public interface QuoteDAO {

	public Quote createQuote(Quote quote);

	public Quote updateQuote(long id, Quote quote);

	public void deleteQuote(Long id);

	public List<Quote> getQuotes();
}
