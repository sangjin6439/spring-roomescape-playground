package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public Reservation insert(ReservationRequestDto reservationDto) {

        Map<String, Object> reservations = new HashMap<>();
        reservations.put("name", reservationDto.getName());
        reservations.put("date", reservationDto.getDate());
        reservations.put("time", reservationDto.getTime());
        Long newId = simpleJdbcInsert.executeAndReturnKeyHolder(reservations).getKey().longValue();

        return new Reservation(newId, reservationDto.getName(), reservationDto.getDate(), reservationDto.getTime());
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM reservation where id = ?", new Object[]{id}, (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public boolean deleteById(Long id) {
        if (jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id) == 1) {
            return true;
        }
        ;
        return false;
    }
}
