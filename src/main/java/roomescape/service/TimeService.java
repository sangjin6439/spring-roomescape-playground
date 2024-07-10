package roomescape.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roomescape.domain.Time;
import roomescape.dto.RequestTimeDto;
import roomescape.repository.TimeRepository;

@Service
@Transactional(readOnly = true)
public class TimeService {

    private final TimeRepository timeRepository;

    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Transactional
    public Time createTime(RequestTimeDto timeDto) {

        Map<String, String> times = new HashMap<>();
        times.put("time", timeDto.getTime());

        Time time = timeRepository.insert(times);

        return time;
    }

    public List<Time> findAll() {
        List<Time> times = timeRepository.finaAll();
        return times;
    }

    @Transactional
    public boolean deleteById(@PathVariable("id") Long id) {
        boolean time = timeRepository.deleteById(id);
        return time;
    }
}
