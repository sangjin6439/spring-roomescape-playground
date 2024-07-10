package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.RequestReservationDto;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TimeRepository timeRepository;

    public ReservationService(final ReservationRepository reservationRepository, final TimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Reservation createReservation(RequestReservationDto reservationDto){

        Map<String, String> reservations = new HashMap<>();
        reservations.put("name", reservationDto.getName());
        reservations.put("date", reservationDto.getDate());
        reservations.put("time_id", reservationDto.getTime());

        Reservation reservation = reservationRepository.insert(reservations);

        return  reservation;
    }

    public List<Reservation> findAll(){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations;
    }

    public Reservation findById(Long id){
        Reservation reservation = reservationRepository.findById(id);
        return reservation;
    }

    @Transactional
    public boolean deleteById(Long id){
        boolean removed = reservationRepository.deleteById(id);
        return removed;
    }

}
