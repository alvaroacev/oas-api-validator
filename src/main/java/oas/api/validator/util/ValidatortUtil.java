package oas.api.validator.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatortUtil {

    private ValidatortUtil() { }

    private static final Logger log = LoggerFactory.getLogger(ValidatortUtil.class);

    /**
     * Load a response file with the given name.
     *
     * @param responseNameAndExtension The name of the response to load
     * @return The response JSON as a String, or <code>null</code> if it cannot be loaded
     * @throws FileNotFoundException 
     */
    public static String loadResponse(final String responseNameAndExtension) throws FileNotFoundException {
        return loadResource("/responses/" + responseNameAndExtension);
    }

	public static String loadStream(final InputStream stream) {
		try {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append('\n');
			}
			return builder.toString();
		} catch (final Exception e) {
			log.error("error occured {}",e);
			return null;
		}
	}


	public static String loadResource(final String path) throws FileNotFoundException {
		log.info("loading resource {}", path);
		return loadStream(new FileInputStream(path));
    }

}
