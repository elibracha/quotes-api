package quotes.api.daos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quotes.api.daos.interfaces.QuoteDAO;
import quotes.api.model.Quote;
import quotes.api.repositories.QuoteRepository;

@Service
public class QuoteDAOImpl implements QuoteDAO {

	private QuoteRepository quoteRepository;

	public QuoteDAOImpl(QuoteRepository quoteRepository) {
		this.quoteRepository = quoteRepository;
	}

	public List<Quote> getQuotes() {
		return quoteRepository.findAll();
	}

	@Transactional
	public Quote createQuote(Quote quote) {
		if (quoteRepository.findDeletedByName(quote.getName()) != null) {
			Quote recycledQuote = quoteRepository.findDeletedByName(quote.getName());
			quoteRepository.recycle(recycledQuote.getId());

			recycledQuote.setItems(quote.getItems());
			recycledQuote.setPrice(quote.getPrice());

			return recycledQuote;
		}

		return quoteRepository.saveAndFlush(quote);
	}

	@Transactional
	public Quote updateQuote(long id, Quote quote) {
		Quote originalQuote = quoteRepository.findById(id).get();

		originalQuote.setItems(quote.getItems());
		originalQuote.setName(quote.getName());
		originalQuote.setPrice(quote.getPrice());

		return originalQuote;
	}

	@Transactional
	public void deleteQuote(Long id) {
		quoteRepository.softDelete(id);
	}
}
