package Itstime.planear.exception;

import Itstime.planear.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PlanearControllerAdvice {

    @ExceptionHandler(PlanearException.class)
    public ResponseEntity<ApiResponse<?>> handlePlanearException(PlanearException e) {
        log.warn("PlanearException", e);
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e){
        log.error("Valid Exception");
        return ResponseEntity.status(e.getStatusCode())
                .body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.status(500)
                .body(ApiResponse.fail(e.getMessage()));
    }
}
