package oas.api.validator;

import static com.atlassian.oai.validator.model.Request.Method.GET;
import static oas.api.validator.util.ValidatortTestUtil.assertPass;

import org.junit.Ignore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Response;
import com.atlassian.oai.validator.model.SimpleResponse;

/**
 * An example that uses the {@link OpenApiInteractionValidator} to validate response
 * mediated by the Spring RestTemplate against a Service Provider implementing the API specification.
 *
 */
public class ServiceProviderResponseValidationTest {

    private static final String OAS_API_GPI_V3_SDK = "/oas/v3/api-gpi-v3_0_11-SDK.yaml";
    private final OpenApiInteractionValidator classUnderTest = OpenApiInteractionValidator
			.createForSpecificationUrl(OAS_API_GPI_V3_SDK).build();
    private RestTemplate restTemplate;

	@Ignore // setup a service provider listening on this URI and returning the appropriate response
	public void validate_payment_status() {
		String url = "http://localhost:8080/payments/97ed4827-7b6f-4491-a06f-b548d5a7512d/transactions";
		restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity =  restTemplate.getForEntity(url, String.class);
		final Response response = SimpleResponse.Builder.ok().withContentType("application/json")
				.withBody(responseEntity.getBody()).build();

		assertPass(classUnderTest.validateResponse("/payments/{uetr}/transactions", GET, response));
	}

	
    


}
