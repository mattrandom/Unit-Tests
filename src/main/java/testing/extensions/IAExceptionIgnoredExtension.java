package testing.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.util.logging.Logger;

public class IAExceptionIgnoredExtension implements TestExecutionExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(IAExceptionIgnoredExtension.class.getName());

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable instanceof IllegalArgumentException) {
            LOGGER.info("Just ignored IllegalArgumentException!");
        } else {
            throw throwable;
        }
    }
}
