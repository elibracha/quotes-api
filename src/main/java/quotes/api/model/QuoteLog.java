package quotes.api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import quotes.api.model.enums.Operation;

@Entity
@Table(name = "quote_log")
public class QuoteLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	@GenericGenerator(name = "native", strategy = "increment")
	private long id;
	@Column(name = "created_date")
	@CreationTimestamp
	private Timestamp createdDate;
	@Column(name = "quote_id")
	private Long quoteId;
	@Enumerated(EnumType.STRING)
	private Operation operation;
	@Column(name = "error_code")
	private int errorCode;
	@Column(length = 9999)
	private String message;

	public QuoteLog() {
		super();
	}

	public QuoteLog(Timestamp createdDate, Long quoteId, Operation operation, int errorCode, String message) {
		super();
		this.createdDate = createdDate;
		this.quoteId = quoteId;
		this.operation = operation;
		this.errorCode = errorCode;
		this.message = message;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "QuoteLog [id=" + id + ", createdDate=" + createdDate + ", quoteId=" + quoteId + ", operation="
				+ operation + ", errorCode=" + errorCode + ", message=" + message + "]";
	}

}
