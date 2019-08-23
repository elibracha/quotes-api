package quotes.api.exceptions;

public class MissMatchItemNameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissMatchItemNameException() {
		super("item id exsist but name value is not the same.");
	}

}
