package uet.kltn.judgment.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uet.kltn.judgment.constant.Power;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.judgment.UpdateJudgmentRequestDto;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.JudgmentService;
import uet.kltn.judgment.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/judgment")
public class JudgmentController extends GenController {
    @Autowired
    private JudgmentService judgmentService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/{uid}")
    public ResponseEntity<?> getJudgment(@PathVariable(value = "uid") String uid,
                                         @CurrentUser UserPrincipal userPrincipal,
                                         Authentication authentication) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            Judgment judgment = judgmentService.getJudgmentByUid(uid);
            if (judgment == null) {
                return responseUtil.getNotFoundResponse(uid);
            }
            return responseUtil.getSuccessResponse(judgment);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getJudgmentsList(@CurrentUser UserPrincipal userPrincipal,
                                              Authentication authentication,
                                              @RequestParam Map<String, Object> request) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            PageDto response = new PageDto();
            Map<String, Object> params = utils.getExpressionAndParams(request, Judgment.class);
            ExpressionDto expressionDto = (ExpressionDto) params.get("expression");
            response = judgmentService.getAllJudgments(expressionDto);

            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PutMapping(path = "/{uid}")
    public ResponseEntity<?> updateJudgment(@PathVariable(value = "uid") String uid,
                                            @Valid @RequestBody UpdateJudgmentRequestDto updateJudgmentRequestDto,
                                            @CurrentUser UserPrincipal userPrincipal,
                                            Authentication authentication) {
        try {
            if (userPrincipal == null ||
                    (userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()) && !userPrincipal.getId().equals(uid))) {
                return responseUtil.getForbiddenResponse();
            }

            Judgment judgment = judgmentService.getJudgmentByUid(uid);
            if (judgment == null) {
                return responseUtil.getNotFoundResponse(uid);
            }
            judgment = judgmentService.updateJudgment(judgment, updateJudgmentRequestDto);
            return responseUtil.getSuccessResponse(judgment);

        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteJudgment(@Valid @RequestParam(name = "uids") List<String> uids,
                                            @CurrentUser UserPrincipal userPrincipal,
                                            Authentication authentication) {
        try {
            if (userPrincipal == null || (userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()))) {
                return responseUtil.getForbiddenResponse();
            }

           List<Judgment> judgments = judgmentService.getJudgmentByUids(uids);
            if(judgments.size() != uids.size()) {
                return responseUtil.getBadRequestResponse("Not found all uids or your role is lower than some uids");
            }
            judgmentService.deleteListJudgment(judgments);
            return responseUtil.getSuccessResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}