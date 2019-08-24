package quotes.api.daos;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import quotes.api.daos.interfaces.QuoteLogDAO;
import quotes.api.model.QuoteLog;
import quotes.api.model.enums.Operation;
import quotes.api.model.enums.StatusCode;
import quotes.api.repositories.QuoteLogRepository;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class QuoteLogDAOImpl implements QuoteLogDAO {

	private QuoteLogRepository quoteLogRepositroy;

	public QuoteLogDAOImpl(QuoteLogRepository quoteLogRepositroy) {
		this.quoteLogRepositroy = quoteLogRepositroy;
	}

	public void logQuoteErrorToDB(Operation operation, String message) {
		QuoteLog quoteLog = new QuoteLog();

		quoteLog.setOperation(operation);
		quoteLog.setErrorCode(StatusCode.ERROR);
		quoteLog.setMessage(message);

		quoteLogRepositroy.saveAndFlush(quoteLog);
	}

}
