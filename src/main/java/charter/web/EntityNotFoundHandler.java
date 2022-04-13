package charter.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestControllerAdvice
public class EntityNotFoundHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException e,
                                                                             HttpServletRequest request) {
        return ExceptionHandlerUtil.basicErrorResponse(e, HttpStatus.NOT_FOUND, request);
    }
}
