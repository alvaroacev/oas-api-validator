package oas.api.validator;

import org.junit.Test;

public class OpenAPIValidatorTest {

	@Test
	public void testMain() {
		OpenAPIValidator.main(new String[] { "-oas", "/Users/alface/Documents/development/sources/oas-api-validator/src/test/resources/oas/v3/api-gpi-v3_0_11-SDK.yaml", //
				"-response", "/Users/alface/Documents/development/sources/oas-api-validator/src/test/resources/responses/payment_transaction_details_serial.json", // 
				"-apiPath", "/payments/{uetr}/transactions", // 
				"-httpMethod", "GET" });

	}

}
