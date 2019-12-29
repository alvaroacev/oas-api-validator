package oas.api.validator;

import static com.atlassian.oai.validator.model.Request.Method.GET;
import static com.atlassian.oai.validator.model.Request.Method.PUT;
import static oas.api.validator.util.ValidatortTestUtil.assertPass;
import static oas.api.validator.util.ValidatortTestUtil.loadResponse;

import org.junit.Test;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Response;
import com.atlassian.oai.validator.model.SimpleResponse;

/**
 * An example that uses the {@link OpenApiInteractionValidator} to validate responses stores as resources 
 *
 */
public class ResourceResponseValidationTest {

	private static final String OAS_API_GPI_V3_SDK = "/oas/v3/api-gpi-v3_0_11-SDK.yaml";
	private final OpenApiInteractionValidator classUnderTest = OpenApiInteractionValidator
			.createForSpecificationUrl(OAS_API_GPI_V3_SDK).build();

	@Test
	public void validate_payment_status() {
		String loadJsonResponse = loadResponse("gpi-payment_status_response.json");
		final Response response = SimpleResponse.Builder.ok().withContentType("application/json")
				.withBody(loadJsonResponse).build();

		assertPass(classUnderTest.validateResponse("/payments/status", PUT, response));
	}

	@Test
	public void validate_payment_transaction_details_serial() {
		String loadJsonResponse = loadResponse("payment_transaction_details_serial.json");
		final Response response = SimpleResponse.Builder.ok().withContentType("application/json")
				.withBody(loadJsonResponse).build();

		assertPass(classUnderTest.validateResponse("/payments/{uetr}/transactions", GET, response));
	}

	@Test
	public void validate_payment_transaction_details_cover() {
		String loadJsonResponse = loadResponse("payment_transaction_details_cover.json");
		final Response response = SimpleResponse.Builder.ok().withContentType("application/json")
				.withBody(loadJsonResponse).build();

		assertPass(classUnderTest.validateResponse("/payments/{uetr}/transactions", GET, response));
	}
	
	
}
