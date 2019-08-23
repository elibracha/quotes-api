package quotes.api.errors;

public class QuoteError {

	private String level;
	private String description;
	private int errorCode;

	public QuoteError() {
		super();
	}

	public QuoteError(String level, String description, int errorCode) {
		super();
		this.level = level;
		this.description = description;
		this.errorCode = errorCode;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "QuoteError [level=" + level + ", description=" + description + ", errorCode=" + errorCode + "]";
	}

}
