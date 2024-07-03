package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import roomescape.domain.Reservation;

public class ReservationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Reservation reservation) {
        String sql = "INSERT INTO reservation (?,?,?)";
        jdbcTemplate.update(sql, reservation.getName(), reservation.getDate(), reservation.getTime());
    }
}
