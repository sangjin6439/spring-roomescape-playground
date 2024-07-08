package roomescape.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequestDto;
import roomescape.global.CustomException;
import roomescape.global.ErrorCode;
import roomescape.repository.ReservationRepository;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(final ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "reservation";
    }

    @GetMapping("/reservations")
    @ResponseBody
    public ResponseEntity<List<Reservation>> reservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/reservation/{id}")
    @ResponseBody
    public ResponseEntity<Reservation> reservation(@PathVariable("id") Long id) {
        Reservation reservation = reservationRepository.findById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @PostMapping("/reservations")
    @ResponseBody
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationRequestDto reservationDto) {

        Reservation reservation = reservationRepository.insert(reservationDto);

        URI location = UriComponentsBuilder.fromPath("/reservations/{id}").buildAndExpand(reservation.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).body(reservation);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        boolean removed = reservationRepository.deleteById(id);
        if (!removed) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}