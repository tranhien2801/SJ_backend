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
import uet.kltn.judgment.dto.request.judgment.FilterJudgmentRequestDto;
import uet.kltn.judgment.dto.request.judgment.JudgmentErrorRequestDto;
import uet.kltn.judgment.dto.request.judgment.LikedJudgmentRequestDto;
import uet.kltn.judgment.dto.request.judgment.UpdateJudgmentRequestDto;
import uet.kltn.judgment.dto.response.judgment.JudgmentResponseDto;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.JudgmentError;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.JudgmentErrorService;
import uet.kltn.judgment.service.JudgmentService;
import uet.kltn.judgment.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/judgment")
public class JudgmentController extends GenController {
    @Autowired
    private JudgmentService judgmentService;

    @Autowired
    private JudgmentErrorService judgmentErrorService;

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
            JudgmentResponseDto judgment = judgmentService.getJudgmentResponseDtoByUid(uid);
            if (judgment == null) {
                return responseUtil.getNotFoundResponse(uid);
            }
            return responseUtil.getSuccessResponse(judgment);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PostMapping("/list")
    public ResponseEntity<?> getJudgmentsList(@CurrentUser UserPrincipal userPrincipal,
                                              Authentication authentication,
                                              @RequestParam Map<String, Object> request,
                                              @RequestBody(required = false) FilterJudgmentRequestDto filterJudgmentRequestDto) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            PageDto response = new PageDto();
            Map<String, Object> params = utils.getExpressionAndParams(request, Judgment.class);
            ExpressionDto expressionDto = (ExpressionDto) params.get("expression");
            response = judgmentService.getJudgmentsByFilter(expressionDto, filterJudgmentRequestDto);
            if (response == null) {
                return responseUtil.getInternalServerErrorResponse();
            }
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping("/judgment-level")
    public ResponseEntity<?> getCourtLevels(Authentication authentication) {
        try {
            Set<String> response = judgmentService.getJudgmentLevels();
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PostMapping("/liked")
    public ResponseEntity<?> likedJudgment(@CurrentUser UserPrincipal userPrincipal,
                                           Authentication authentication,
                                           @RequestBody LikedJudgmentRequestDto likedJudgmentRequestDto){
        try {
            if (userPrincipal == null || !userPrincipal.getId().equals(likedJudgmentRequestDto.getUserUid())) {
                return responseUtil.getForbiddenResponse();
            }

            if (!judgmentService.userLikedJudgment(likedJudgmentRequestDto)) {
                return responseUtil.getNotFoundResponse(likedJudgmentRequestDto.getJudgmentUid());
            }
            return responseUtil.getSuccessResponse(likedJudgmentRequestDto);

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

    @PostMapping("/error")
    public ResponseEntity<?> reportJudgmentError(@CurrentUser UserPrincipal userPrincipal,
                                                 @Valid @RequestBody JudgmentErrorRequestDto judgmentErrorRequestDto,
                                                 Authentication authentication) {
        try {
            if (userPrincipal == null || !userPrincipal.getId().equals(judgmentErrorRequestDto.getUserUid())) {
                return responseUtil.getForbiddenResponse();
            }
            JudgmentError judgmentError = judgmentErrorService.createJudgmentError(judgmentErrorRequestDto);
            if (judgmentError == null) {
                return responseUtil.getNotFoundResponse("Bản án không phù hợp hoặc người dùng không đúng");
            }
            return responseUtil.getSuccessResponse(judgmentErrorRequestDto);
        } catch (Exception e) {
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
