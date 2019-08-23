package quotes.api.daos.interfaces;

import quotes.api.model.enums.Operation;

public interface QuoteLogDAO {
	public void logQuoteErrorToDB(Operation operation, String message);
}
