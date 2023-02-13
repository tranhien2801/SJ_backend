package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.response.unit.UnitResponseDto;
import uet.kltn.judgment.model.CaseType;
import uet.kltn.judgment.model.Unit;
import uet.kltn.judgment.respository.CaseRepository;
import uet.kltn.judgment.respository.CaseTypeRepository;
import uet.kltn.judgment.respository.UnitRepository;
import uet.kltn.judgment.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UnitService {
    @Autowired
    private UnitRepository unitRespository;



    public List<UnitResponseDto> getAllUnits() {
        List<Unit> units = unitRespository.findAllByState(State.ACTIVE.getId());
        List<UnitResponseDto> unitResponseDtos = new ArrayList<>();
        for (Unit unit : units) {
            unitResponseDtos.add(new UnitResponseDto(unit.getUid(),unit.getUnitName(), unit.getState(), unit.getCreated(), unit.getModified()));
        }
        return unitResponseDtos;
    }


}
