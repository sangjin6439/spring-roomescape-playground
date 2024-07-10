package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.RequestReservationDto;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation insert(RequestReservationDto reservationDto) {

        Map<String, Object> reservations = new HashMap<>();
        reservations.put("name", reservationDto.getName());
        reservations.put("date", reservationDto.getDate());
        reservations.put("time_id", reservationDto.getTime());
        Long reservationId = simpleJdbcInsert.executeAndReturnKeyHolder(reservations).getKey().longValue();

        return new Reservation(reservationId, reservationDto.getName(), reservationDto.getDate(), findTimeById(Long.valueOf(reservationDto.getTime())));
    }

    private Time findTimeById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?", new Object[]{id}, ((rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time"))));
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id",
                (rs, rowNum) -> new Reservation(rs.getLong("reservation_id"), rs.getString("name"), rs.getString("date"), new Time(rs.getLong("time_id"), rs.getString("time_value")))
        );
    }

    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT r.id AS reservation_id, r.name, r.date, t.id AS time_id, t.time AS time_value FROM reservation AS r INNER JOIN time AS t ON r.time_id = t.id WHERE r.id = ?", new Object[]{id},
                (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), new Time(rs.getLong("time_id"), rs.getString("time_value")))
        );
    }

    public boolean deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) == 1;
    }
}