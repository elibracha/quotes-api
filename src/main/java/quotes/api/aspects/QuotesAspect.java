package quotes.api.aspects;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import quotes.api.model.Quote;
import quotes.api.model.QuoteLog;
import quotes.api.model.enums.Operation;
import quotes.api.repositories.QuoteLogRepository;

@Aspect
@Component
public class QuotesAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuotesAspect.class);
	private static final int FAILED_CODE = 1;
	private static final int SUCCESS_CODE = 0;

	private QuoteLogRepository quoteLogRepository;

	public QuotesAspect(QuoteLogRepository quoteLogRepository) {
		this.quoteLogRepository = quoteLogRepository;
	}

	@Pointcut("execution(* quotes.api.controllers.QuotesController.post*(..))")
	public void post() {
	}

	@Pointcut("execution(* quotes.api.controllers.QuotesController.delete*(..))")
	public void delete() {
	}

	@Pointcut("execution(* quotes.api.controllers.QuotesController.update*(..))")
	public void update() {
	}

	@Around("post() || delete() || update()")
	public Object logBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		QuoteLog quoteLog = new QuoteLog();
		quoteLog.setCreatedDate(new Timestamp(Calendar.getInstance().getTime().getTime()));

		switch (request.getMethod()) {
			case "POST":
				quoteLog.setOperation(Operation.CREATE);
				break;
			case "PUT":
				quoteLog.setOperation(Operation.UPDATE);
				break;
			case "DELETE":
				quoteLog.setOperation(Operation.DELETE);
				break;
		}

		Object result = null;

		try {
			result = proceedingJoinPoint.proceed();
			quoteLog.setQuoteId(request.getMethod().equals("POST") ? 
					((Quote)((ResponseEntity<Quote>) result).getBody()).getId()
					: (long) proceedingJoinPoint.getArgs()[0]);

			quoteLog.setErrorCode(SUCCESS_CODE);
			
		} catch (Throwable e) {
			quoteLog.setQuoteId(null);
			quoteLog.setErrorCode(FAILED_CODE);
			quoteLog.setMessage(e.getMessage());
			
			LOGGER.error(e.toString());
		} 
		
		quoteLogRepository.save(quoteLog);
		return result;

	}

}
