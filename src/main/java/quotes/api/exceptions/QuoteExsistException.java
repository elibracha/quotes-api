package quotes.api.exceptions;

public class QuoteExsistException extends RuntimeException {

	public QuoteExsistException(String message) {
		super(message);
	}
}
