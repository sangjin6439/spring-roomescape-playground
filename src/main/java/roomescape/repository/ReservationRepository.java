package roomescape.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import roomescape.domain.Reservation;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Reservation insert(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(cn->{
            PreparedStatement ps = cn.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, reservation.getName());
            ps.setString(2, reservation.getDate());
            ps.setString(3, reservation.getTime());
            return ps;
            }, keyHolder);
        reservation.setId(keyHolder.getKey().longValue());
        return reservation;
    }

    public List<Reservation> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation", (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM reservation where id = ?", new Object[]{id}, (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), rs.getString("time")));
    }

    public boolean deleteById(Long id) {
        if(jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id)==1){
            return true;
        };
        return false;
    }
}
