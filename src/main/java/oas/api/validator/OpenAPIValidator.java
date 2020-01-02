package oas.api.validator;

import static oas.api.validator.util.ValidatortUtil.loadResource;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.model.Response;
import com.atlassian.oai.validator.model.SimpleResponse;
import com.atlassian.oai.validator.report.LevelResolver;
import com.atlassian.oai.validator.report.SimpleValidationReportFormat;
import com.atlassian.oai.validator.report.ValidationReport;

/**
 * 
 * This program performs a validation of the request and/or response against the
 * Open API Specification (OAS).
 * To process the validation, main method as input following arguments:
 * <p> oas: URI to the OAS in YAML format
 * <p> response: URI to the API Response in JSON format
 * <p> httpMethod
 * <p> apiPath
 * <p> request: URI to the API Request in JSON format
 *
 */
public class OpenAPIValidator {
	
	private static final Logger log = LoggerFactory.getLogger(OpenAPIValidator.class);

	public static void main(String[] args) {
		final Options options = new Options();
		options.addOption(Option.builder("oas").argName("oas").hasArg().desc("Open API Specification URI in YAML format.").required().build());
		options.addOption(Option.builder("response").argName("response").hasArg().desc("HTTP response URI in JSON formart.").required().build());
		options.addOption(Option.builder("httpMethod").argName("httpMethod").hasArg().desc("HTTP method (e.g. GET, PUT, POST, DELETE.").required().build());
		options.addOption(Option.builder("apiPath").argName("apiPath").hasArg().desc("HTTP method (e.g. /payments/{id}/transactions).").required().build());
		options.addOption(Option.builder("request").argName("request").hasArg().desc("HTTP request URI in JSON formart.").build());

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);

			String oasFilePath = cmd.getOptionValue("oas");
			String responseFilePath = cmd.getOptionValue("response");
			Request.Method method = Request.Method.valueOf(cmd.getOptionValue("httpMethod"));
			String apiPath = cmd.getOptionValue("apiPath");
			//TODO add request argument
			//String requestFilePath = cmd.getOptionValue("request");

			final OpenApiInteractionValidator classUnderTest = OpenApiInteractionValidator
					.createForSpecificationUrl(oasFilePath).withLevelResolver( //
							LevelResolver.create() //
									.withLevel("validation.schema.required", ValidationReport.Level.INFO) //
									.withLevel("validation.response.body.missing", ValidationReport.Level.INFO) //
									.build()) //
					.build();

			//TODO load request and validate (if argument passed)
			//String loadJsonRequest = loadResource(requestFilePath);
			String loadJsonResponse = loadResource(responseFilePath);
			log.debug("Response: {}", loadJsonResponse);
			final Response response = SimpleResponse.Builder.ok().withContentType("application/json")
					.withBody(loadJsonResponse).build();

			ValidationReport report = classUnderTest.validateResponse(apiPath, method, response);

			if (report.hasErrors()) {
				log.error(SimpleValidationReportFormat.getInstance().apply(report));

				System.exit(1);
			} else {
				log.info("Validation passed");
			}

		} catch (FileNotFoundException e) {
			log.error("Cannot load resource {}", e.getMessage());
			System.exit(1);
		} catch (ParseException e) {
			log.error("Parsing failed.  Reason: {}", e.getMessage());
			formatter.printHelp("Open API validator: {}", options);
			System.exit(1);
		} catch (RuntimeException e) {
			log.error("Parsing failed.  Reason: {}", e.getMessage());
			System.exit(1);
		}
	}
}

