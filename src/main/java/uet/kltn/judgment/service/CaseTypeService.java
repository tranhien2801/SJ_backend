package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.response.caseType.CaseTypeResponseDto;
import uet.kltn.judgment.model.CaseType;
import uet.kltn.judgment.respository.CaseRepository;
import uet.kltn.judgment.respository.CaseTypeRepository;
import uet.kltn.judgment.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CaseTypeService {
    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CaseTypeRepository caseTypeRepository;

    public Set<CaseTypeResponseDto> getAllCaseTypes() {
        Set<CaseType> caseTypes = caseTypeRepository.findAllByState(State.ACTIVE.getId());
        System.out.println(caseTypes);
        if (caseTypes.size() == 0)  return null;
        Set<CaseTypeResponseDto> caseTypeResponseDtos = new HashSet<>();
        for (CaseType caseType : caseTypes) {
            caseTypeResponseDtos.add(new CaseTypeResponseDto(caseType.getUid(), caseType.getCaseTypeName(), caseType.getState(),caseType.getCreated(), caseType.getModified()));
        }
         return caseTypeResponseDtos;
    }

    public List<CaseType> createCaseTypes() {
        Set<String> caseTypeNames = caseRepository.findCaseTypes();
        List<CaseType> caseTypes = new ArrayList<>();
        for(String name : caseTypeNames) {
            caseTypes.add(new CaseType(Utils.uuid(), name));
        }

        caseTypeRepository.saveAll(caseTypes);
        return caseTypes;

    }
}
