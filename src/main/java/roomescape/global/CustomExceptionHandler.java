package roomescape.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleException(CustomException e){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler
    public ResponseEntity handleNumberFormatException(NumberFormatException e){
        return ResponseEntity.badRequest().build();
    }
}
