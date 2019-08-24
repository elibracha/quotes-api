package quotes.api.api;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import quotes.api.errors.QuoteError;
import quotes.api.model.Quote;

public class ApiTest extends AbstractTest {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void postQuoteWithEmptyName() throws Exception {
		String uri = "/api/v1/quotes";
		Quote quote = new Quote();
		quote.setName("");
	
		String inputJson = super.mapToJson(quote);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		QuoteError error = super.mapFromJson(mvcResult.getResponse().getContentAsString(), QuoteError.class);
		assertEquals(error.getDescription(), "name field can not be empty");
	}
	
	@Test
	public void postQuoteWithNegativePrice() throws Exception {
		String uri = "/api/v1/quotes";
		Quote quote = new Quote();
		quote.setName("random");
		quote.setPrice(-1L);
		
		String inputJson = super.mapToJson(quote);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(400, status);
		QuoteError error = super.mapFromJson(mvcResult.getResponse().getContentAsString(), QuoteError.class);
		assertEquals(error.getDescription(), "price can not be a negative number");
	}
	
	
}
