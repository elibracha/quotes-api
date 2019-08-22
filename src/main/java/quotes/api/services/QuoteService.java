package quotes.api.services;

import java.util.List;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quotes.api.model.Item;
import quotes.api.model.Quote;
import quotes.api.repositories.ItemRepository;
import quotes.api.repositories.QuoteRepository;
import quotes.api.responses.QuoteError;

@Service
public class QuoteService {

	private QuoteRepository quoteRepository;
	private ItemRepository itemRepository;

	public QuoteService(QuoteRepository quoteRepository, ItemRepository itemRepository) {
		super();
		this.quoteRepository = quoteRepository;
		this.itemRepository = itemRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<List<Quote>> getQuotes() {
		return ResponseEntity.ok(quoteRepository.findAll());
	}

	@Transactional(noRollbackFor = {JdbcSQLException.class, DataIntegrityViolationException.class})
	public ResponseEntity<Object> postQuotes(Quote quote) {
		if (quote.getItems() != null)
			for (Item item : quote.getItems()) {
				if (!itemRepository.existsById(item.getId())) {
					itemRepository.save(item);
				}
			}
		return ResponseEntity.ok(quoteRepository.save(quote));
	}

	@Transactional(noRollbackFor = {JdbcSQLException.class, DataIntegrityViolationException.class})
	public ResponseEntity<Object> updateQuotes(long id, Quote quote) {
		if (!quoteRepository.findById(id).isPresent()) {
			return ResponseEntity.badRequest()
					.body(new QuoteError("error", String.format("no quote with id of %s found.", id), "1"));
		}

		Quote originalQuote = quoteRepository.findById(id).get();

		originalQuote.setItems(quote.getItems());
		originalQuote.setName(quote.getName());
		originalQuote.setPrice(quote.getPrice());

		return ResponseEntity.ok(quoteRepository.save(originalQuote));
	}

	@Transactional
	public void deleteQuotes(Long id) {
		quoteRepository.delete(id);
	}
}
