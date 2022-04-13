package charter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(IllegalStateException e,
                                                                             HttpServletRequest request) {
        return ExceptionHandlerUtil.basicErrorResponse(e, HttpStatus.PRECONDITION_FAILED, request);
    }
}
