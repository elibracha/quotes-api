package quotes.api.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import quotes.api.daos.QuoteLogDAOImpl;
import quotes.api.model.enums.Operation;

@Aspect
@Component
public class QuotesAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuotesAspect.class);

	private QuoteLogDAOImpl logService;

	public QuotesAspect(QuoteLogDAOImpl logService) {
		this.logService = logService;
	}

	@Pointcut("execution(* quotes.api.services.QuoteService.post*(..))")
	public void postQuote() {
	}

	@Pointcut("execution(* quotes.api.services.QuoteService.delete*(..))")
	public void deleteQuote() {
	}

	@Pointcut("execution(* quotes.api.services.QuoteService.update*(..))")
	public void updateQuote() {
	}

	@Before("execution(* quotes.api.handlers.QuotesExceptionHandler.handleMethodArgumentNotValid(..))")
	public void handleVaildationLog(JoinPoint joinPoint) {
		logToFileAndDB((Throwable) joinPoint.getArgs()[0]);
	}

	@AfterThrowing(pointcut = "postQuote() || deleteQuote() || updateQuote()", throwing = "ex")
	public void handleExceptionLog(JoinPoint joinPoint, Throwable ex) {
		logToFileAndDB(ex);
	}

	private void logToFileAndDB(Throwable e) {
		LOGGER.error(e.getMessage());
		logService.logQuoteErrorToDB(getOperation(), e.getMessage());
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
		default:
			return Operation.NOT_SUPPORTED;
		}
	}

}
