package oas.api.validator.util;

import static oas.api.validator.util.ValidatortUtil.loadStream;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.oai.validator.report.JsonValidationReportFormat;
import com.atlassian.oai.validator.report.ValidationReport;

public class ValidatortTestUtil {

	private ValidatortTestUtil() {
	}

	private static final Logger log = LoggerFactory.getLogger(ValidatortUtil.class);

	/**
	 * Assert that validation has passed.
	 */
	public static void assertPass(final ValidationReport report) {
		log.debug(JsonValidationReportFormat.getInstance().apply(report));
		assertTrue("Expected no validation errors but found some. Enable trace logging for more details.",
				report.getMessages().isEmpty()
						|| report.getMessages().stream().allMatch(m -> m.getLevel() == ValidationReport.Level.IGNORE));
	}

	/**
	 * Load a response file with the given name.
	 *
	 * @param responseNameAndExtension The name of the response to load
	 * @return The response JSON as a String, or <code>null</code> if it cannot be
	 *         loaded
	 */
	public static String loadResponse(final String responseNameAndExtension) {
		return loadResource("/responses/" + responseNameAndExtension);
	}

	/**
	 * Load a request file with the given name and extension.
	 *
	 * @param requestNameAndExtension The name of the request to load
	 *
	 * @return The response as a String, or <code>null</code> if it cannot be loaded
	 */
	public static String loadRequest(final String requestNameAndExtension) {
		return loadResource("/requests/" + requestNameAndExtension);
	}

	public static String loadResource(final String path) {
		log.info("loading resource {}", path);
		final InputStream stream = ValidatortUtil.class.getResourceAsStream(path);
		return loadStream(stream);

	}

}
