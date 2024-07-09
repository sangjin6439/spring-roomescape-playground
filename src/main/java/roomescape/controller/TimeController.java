package roomescape.controller;

import org.springframework.http.HttpHeaders;
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
import roomescape.domain.Time;
import roomescape.dto.RequestTimeDto;
import roomescape.global.CustomException;
import roomescape.global.ErrorCode;
import roomescape.repository.TimeRepository;

@Controller
public class TimeController {

    private final TimeRepository timeRepository;

    public TimeController(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @GetMapping("/time")
    public String time() {
        return "time";
    }

    @PostMapping("/times")
    @ResponseBody
    public ResponseEntity<Time> createTime(@Valid @RequestBody RequestTimeDto timeDto) {
        Time time = timeRepository.insert(timeDto);

        URI location = UriComponentsBuilder.fromPath("/times/{id}").buildAndExpand(time.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.created(location).body(time);
    }

    @GetMapping("/times")
    @ResponseBody
    public ResponseEntity<List<Time>> findAllTime() {
        List<Time> times = timeRepository.finaAll();
        return ResponseEntity.ok(times);
    }

    @DeleteMapping("/times/{id}")
    @ResponseBody
    public ResponseEntity<Time> deleteTimeById(@PathVariable("id") Long id) {
        boolean time = timeRepository.deleteById(id);
        if (!time) {
            throw new CustomException(ErrorCode.TIME_NOT_FOUND);
        }
        return ResponseEntity.noContent().build();
    }
}
