package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.response.court.CourtResponseDto;
import uet.kltn.judgment.model.Court;
import uet.kltn.judgment.respository.CourtRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CourtService {
    @Autowired
    private CourtRepository courtRepository;

    public List<CourtResponseDto> getAllCourts() {
        List<Court> courts = courtRepository.findAllByState(State.ACTIVE.getId());
        List<CourtResponseDto> courtResponseDtos = new ArrayList<>();
        for (Court court : courts) {
            courtResponseDtos.add(new CourtResponseDto(court.getUid(), court.getCourtLevel(), court.getAddress(), court.getCourtName(), court.getState(), court.getCreated(), court.getModified()));
        }
        return courtResponseDtos;
    }

    public Set<String> getCourtLevels() {
        return courtRepository.findCourtLevels();
    }
}
