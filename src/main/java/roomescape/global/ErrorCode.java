package roomescape.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST);

    ErrorCode(final HttpStatus httpStatus) {
    }
}
