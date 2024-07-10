package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import roomescape.domain.Time;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("time")
                .usingColumns("time")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(Map<String, Object> times) {
        Long newId = simpleJdbcInsert.executeAndReturnKeyHolder(times).getKey().longValue();
        return newId;
    }

    public List<Time> finaAll() {
        return jdbcTemplate.query("SELECT * FROM time", (rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time")));
    }

    public Time findTimeById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM time WHERE id = ?", new Object[]{id}, ((rs, rowNum) -> new Time(rs.getLong("id"), rs.getString("time"))));
    }

    public boolean deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM time WHERE id = ?", id) == 1;
    }
}
