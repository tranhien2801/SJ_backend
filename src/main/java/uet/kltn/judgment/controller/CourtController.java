package uet.kltn.judgment.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uet.kltn.judgment.dto.response.court.CourtResponseDto;
import uet.kltn.judgment.service.CourtService;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/court")
public class CourtController extends GenController {
    @Autowired
    private CourtService courtService;

    @GetMapping("/list")
    public ResponseEntity<?> getUnitsList(Authentication authentication) {
        try {
            List<CourtResponseDto> response = courtService.getAllCourts();
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping("/court-level")
    public ResponseEntity<?> getCourtLevels(Authentication authentication) {
        try {
            Set<String> response = courtService.getCourtLevels();
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
