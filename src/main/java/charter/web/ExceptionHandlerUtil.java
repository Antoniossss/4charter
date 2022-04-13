package charter.web;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ExceptionHandlerUtil {
    public static ResponseEntity<Map<String, Object>> basicErrorResponse(Exception e, HttpStatus status, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", Instant.now());
        map.put("status", status.value());
        map.put("error", e.getMessage());
        map.put("path", request.getRequestURI());
        return ResponseEntity.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(map);
    }
}
