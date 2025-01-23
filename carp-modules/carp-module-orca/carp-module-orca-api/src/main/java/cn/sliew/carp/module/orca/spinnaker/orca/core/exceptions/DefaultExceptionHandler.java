package cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public boolean handles(Exception e) {
        return true;
    }

    @Override
    public ExceptionHandler.Response handle(String taskName, Exception e) {
        Map<String, Object> exceptionDetails =
                ExceptionHandler.responseDetails(
                        "Unexpected Task Failure", Collections.singletonList(e.getMessage()));
        exceptionDetails.put("stackTrace", Throwables.getStackTraceAsString(e));
        log.warn(format("Error occurred during task %s", taskName), e);
        return new ExceptionHandler.Response(
                e.getClass().getSimpleName(), taskName, exceptionDetails, false);
    }
}
