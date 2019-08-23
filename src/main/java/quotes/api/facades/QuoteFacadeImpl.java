package quotes.api.facades;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import quotes.api.daos.interfaces.ItemDAO;
import quotes.api.daos.interfaces.QuoteDAO;
import quotes.api.facades.interfaces.QuoteFacade;
import quotes.api.model.Quote;

@Service
public class QuoteFacadeImpl implements QuoteFacade {
	private QuoteDAO quoteDAO;
	private ItemDAO itemDAO;
	
	public QuoteFacadeImpl(QuoteDAO quoteDAO, ItemDAO itemDAO) {
		this.quoteDAO = quoteDAO;
		this.itemDAO = itemDAO;
	}

	public List<Quote> getQuotes() {
		return quoteDAO.getQuotes();
	}

	public Object postQuote(Quote quote) {
		itemDAO.createItems(quote.getItems());
		return quoteDAO.createQuote(quote);
	}

	public Object updateQuote(long id, Quote quote) {
		itemDAO.createItems(quote.getItems());
		return quoteDAO.updateQuote(id, quote);
	}

	public void deleteQuote(@PathVariable Long id) {
		quoteDAO.deleteQuote(id);
	}
}
