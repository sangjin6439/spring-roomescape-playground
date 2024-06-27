package roomescape.global;


import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    ErrorCode errorCode;

    public CustomException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
