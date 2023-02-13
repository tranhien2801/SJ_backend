package uet.kltn.judgment.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.kltn.judgment.dto.response.caseType.CaseTypeResponseDto;
import uet.kltn.judgment.model.CaseType;
import uet.kltn.judgment.model.Unit;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.CaseTypeService;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/case-type")
public class CaseTypeController extends GenController{
    @Autowired
    private CaseTypeService caseTypeService;

    @GetMapping("/create")
    public ResponseEntity<?> createCaseTypes() {
        try {
            List<CaseType> caseTypes = caseTypeService.createCaseTypes();
            return responseUtil.getSuccessResponse(caseTypes);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getCaseTypesList(Authentication authentication) {
        try {
            Set<CaseTypeResponseDto> response = caseTypeService.getAllCaseTypes();

            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
