package roomescape.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation {

    private Long id;
    private String name;
    private String localDate;
    private String time;

    public Reservation(final Long id, final String name, final String localDate, final String time) {
        this.id = id;
        this.name = name;
        this.localDate = localDate;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocalDate() {
        return localDate;
    }

    public String getTime() {
        return time;
    }
}
