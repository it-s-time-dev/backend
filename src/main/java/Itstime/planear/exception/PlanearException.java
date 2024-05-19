package Itstime.planear.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class PlanearException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4722196490430111082L;

    private final String message;
    private final HttpStatus httpStatus;

    public PlanearException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
