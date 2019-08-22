package quotes.api.responses;

public class QuoteError {

	private String level;
	private String description;
	private String errorCode;

	public QuoteError() {
		super();
	}

	public QuoteError(String level, String description, String errorCode) {
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "QuoteError [level=" + level + ", description=" + description + ", errorCode=" + errorCode + "]";
	}

}
