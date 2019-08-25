package quotes.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import quotes.api.daos.interfaces.ItemDAO;
import quotes.api.daos.interfaces.QuoteDAO;
import quotes.api.model.Quote;

@Service
public class QuoteService {
	private QuoteDAO quoteDAO;
	private ItemDAO itemDAO;

	public QuoteService(QuoteDAO quoteDAO, ItemDAO itemDAO) {
		this.quoteDAO = quoteDAO;
		this.itemDAO = itemDAO;
	}

	public List<Quote> getQuotes() {
		return quoteDAO.getQuotes();
	}

	public Quote getQuote(long id) {
		return quoteDAO.getQuote(id);
	}

	public Quote postQuote(Quote quote) {
		itemDAO.createItems(quote.getItems());
		return quoteDAO.createQuote(quote);
	}

	public Quote updateQuote(long id, Quote quote) {
		itemDAO.createItems(quote.getItems());
		return quoteDAO.updateQuote(id, quote);
	}

	public void deleteQuote(@PathVariable Long id) {
		quoteDAO.deleteQuote(id);
	}
}
