package uet.kltn.judgment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uet.kltn.judgment.dto.request.user.SignUpRequestDto;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.email.EmailService;

@RestController
public class EmailController extends GenController{
    @Autowired
    private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public ResponseEntity<?> sendConfirmSignup(@RequestBody SignUpRequestDto signUpRequestDto,
                                      @CurrentUser UserPrincipal userPrincipal,
                                      Authentication authentication )     {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            String msg = emailService.sendConfirmSignup(signUpRequestDto);
            return responseUtil.getSuccessResponse(msg);
        } catch (Exception e) {
            return responseUtil.getInternalServerErrorResponse();
        }
    }


}
