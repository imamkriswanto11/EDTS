package com.edts.test.base.handler;

import com.edts.test.base.exception.RateLimitException;
import com.edts.test.base.model.ApiErrorMessage;
import com.edts.test.enumeration.ErrorCode;
import com.edts.test.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RateLimitExceptionHandler extends BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(RateLimitExceptionHandler.class);
    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<String> handleInvalidFieldsInValidJson(final RateLimitException rateLimitException, final HttpServletRequest request) {
        final ApiErrorMessage apiErrorMessage = rateLimitException.toApiErrorMessage(request.getRequestURI());
        logIncomingCallException(rateLimitException, apiErrorMessage);
        return httpService.getErrorResponse(
                HttpStatus.TOO_MANY_REQUESTS.name(),
                rateLimitException.getMessage(),
                HttpStatus.TOO_MANY_REQUESTS);
    }

    private static void logIncomingCallException(final RateLimitException rateLimitException, final ApiErrorMessage apiErrorMessage) {
        LOG.error(String.format("%s: %s", apiErrorMessage.getId(), rateLimitException.getMessage()), rateLimitException);
    }
}
