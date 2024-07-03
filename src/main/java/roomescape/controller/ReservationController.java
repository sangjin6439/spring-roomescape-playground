package roomescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.validation.Valid;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.global.CustomException;
import roomescape.global.ErrorCode;

@Controller
public class ReservationController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private List<Reservation> reservations = new ArrayList<>();
    private AtomicLong index = new AtomicLong(0);

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = jdbcTemplate.query("select * from reservation", (rs, rowNum) -> new Reservation(rs.getLong("id"), rs.getString("name"), rs.getString("date"), rs.getString("time")));
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservation/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> reservation(@PathVariable("id") int id) {
        return new ResponseEntity<>(reservations.get(id),HttpStatus.OK);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationRequestDto requestDto) {

        Reservation reservation = new Reservation(index.incrementAndGet(), requestDto.getName(), requestDto.getDate(), requestDto.getTime());
        reservations.add(reservation);

        URI location = UriComponentsBuilder.fromPath("/reservations/{id}").buildAndExpand(reservation.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") int id) {
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(Long.valueOf(id)));
        if (!removed) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}
