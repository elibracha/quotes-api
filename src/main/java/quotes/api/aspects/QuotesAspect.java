package quotes.api.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import quotes.api.daos.QuoteLogDAOImpl;
import quotes.api.errors.QuoteError;
import quotes.api.exceptions.MissMatchItemNameException;
import quotes.api.model.Quote;
import quotes.api.model.enums.Operation;
import quotes.api.model.enums.StatusCode;

@Aspect
@Component
public class QuotesAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuotesAspect.class);

	private QuoteLogDAOImpl logService;

	public QuotesAspect(QuoteLogDAOImpl logService) {
		this.logService = logService;
	}

	@Pointcut("execution(* quotes.api.facades.QuoteFacadeImpl.post*(..))")
	public void postQuote() {
	}

	@Pointcut("execution(* quotes.api.facades.QuoteFacadeImpl.delete*(..))")
	public void deleteQuote() {
	}

	@Pointcut("execution(* quotes.api.facades.QuoteFacadeImpl.update*(..))")
	public void updateQuote() {
	}

	@Pointcut("execution(* quotes.api.facades.QuoteFacadeImpl.get*(..))")
	public void getQuote() {
	}

	@Before("execution(* quotes.api.handlers.QuotesExceptionHandler.handleMethodArgumentNotValid(..))")
	public void handleVaildationLog(JoinPoint joinPoint) {
		logToFileAndDB((Throwable) joinPoint.getArgs()[0]);
	}

	@Around("postQuote() || deleteQuote() || updateQuote()")
	public Object handleExceptionLog(ProceedingJoinPoint proceedingJoinPoint) {
		try {
			return ((Quote) proceedingJoinPoint.proceed());
		} catch (Throwable e) {
			logToFileAndDB(e);
			return getError(e);
		}

	}

	private void logToFileAndDB(Throwable e) {
		LOGGER.error(e.getMessage());
		logService.logQuoteErrorToDB(getOperation(), e.getMessage());
	}

	private QuoteError getError(Throwable e) {
		if (e instanceof DataIntegrityViolationException)
			return new QuoteError(StatusCode.ERROR.name(), "name is not unique.", StatusCode.ERROR.ordinal());
		else if (e instanceof MissMatchItemNameException)
			return new QuoteError(StatusCode.ERROR.name(), "item id exist but name value is not the same.",
					StatusCode.ERROR.ordinal());
		else
			return new QuoteError(StatusCode.ERROR.name(), "somthing went worrg try again. (object might not be exist)",
					StatusCode.ERROR.ordinal());
	}

	private Operation getOperation() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		switch (request.getMethod()) {
		case "POST":
			return Operation.CREATE;
		case "PUT":
			return Operation.UPDATE;
		case "DELETE":
			return Operation.DELETE;
		case "GET":
			return Operation.GET;
		default:
			return Operation.NOT_SUPPORTED;
		}
	}

}
