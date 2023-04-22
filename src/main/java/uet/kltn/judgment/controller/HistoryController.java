package uet.kltn.judgment.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.judgment.FilterJudgmentRequestDto;
import uet.kltn.judgment.dto.response.judgment.JudgmentResponseDto;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.HistoryService;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/history")
public class HistoryController extends GenController {
    @Autowired
    private HistoryService historyService;

    @PostMapping("/list")
    public ResponseEntity<?> getJudgmentsViewed(@CurrentUser UserPrincipal userPrincipal,
                                                @RequestParam Map<String, Object> request,
                                                Authentication authentication,
                                                @RequestBody(required = false) FilterJudgmentRequestDto filterJudgmentRequestDto) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            PageDto response = new PageDto();
            Map<String, Object> params = utils.getExpressionAndParams(request, Judgment.class);
            ExpressionDto expressionDto = (ExpressionDto) params.get("expression");
            response = historyService.getJudgmentsViewed(expressionDto, userPrincipal.getId(), filterJudgmentRequestDto);
            if (response == null) {
                return responseUtil.getInternalServerErrorResponse();
            }
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
