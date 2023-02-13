package uet.kltn.judgment.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.response.unit.UnitResponseDto;
import uet.kltn.judgment.model.CaseType;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.Unit;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.UnitService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/unit")
public class UnitController extends GenController {
    @Autowired
    private UnitService unitService;

    @GetMapping("/list")
    public ResponseEntity<?> getUnitsList(@CurrentUser UserPrincipal userPrincipal,
                                          Authentication authentication) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            List<UnitResponseDto> response = unitService.getAllUnits();

            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }


}
