package roomescape.global;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    Reservation_Not_Found(HttpStatus.NOT_FOUND);

    ErrorCode(final HttpStatus httpStatus) {
    }
}
