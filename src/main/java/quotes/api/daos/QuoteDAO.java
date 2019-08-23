package quotes.api.daos;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import quotes.api.exceptions.MissMatchItemNameException;
import quotes.api.model.Item;
import quotes.api.model.Quote;
import quotes.api.repositories.ItemRepository;
import quotes.api.repositories.QuoteRepository;

@Service
public class QuoteDAO {

	private QuoteRepository quoteRepository;
	private ItemRepository itemRepository;

	public QuoteDAO(QuoteRepository quoteRepository, ItemRepository itemRepository) {
		this.quoteRepository = quoteRepository;
		this.itemRepository = itemRepository;
	}

	public List<Quote> getQuotes() {
		return quoteRepository.findAll();
	}

	@Transactional
	public Quote postQuote(Quote quote) throws RuntimeException {
		if (quote.getItems() != null)
			for (Item item : quote.getItems()) {
				if (!itemRepository.existsById(item.getId())) {
					itemRepository.save(item);
				} else if (!itemRepository.findById(item.getId()).get().getName().equals(item.getName())) {
					throw new MissMatchItemNameException();

				}
		}

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
