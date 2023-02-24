package uet.kltn.judgment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.DtoField;
import uet.kltn.judgment.constant.Power;
import uet.kltn.judgment.dto.ResponseDto;
import uet.kltn.judgment.dto.request.auth.AuthDto;
import uet.kltn.judgment.dto.request.user.SignUpRequestDto;
import uet.kltn.judgment.dto.response.auth.AuthResponseDto;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.TokenProvider;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController extends GenController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;

    public AuthController() {
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto> login(@RequestBody AuthDto authDto) {
        try {
            User user = userService.getUserByUsername(authDto.getUsername());
            if (user == null) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("Username không tồn tại trong hệ thống");
                errorMap.put(DtoField.PARAM_EMAIL, error);
                return responseUtil.getUnauthorizedResponse(errorMap);
            } else if (!BCrypt.checkpw(authDto.getPassword(), user.getPassword())) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("Password không đúng");
                errorMap.put(DtoField.PARAM_PASSWORD, error);
                return responseUtil.getUnauthorizedResponse(errorMap);
            } else {
                String token = tokenProvider.createToken(user, user.getPower());
                return responseUtil.getSuccessResponse(new AuthResponseDto(token,
                        user.getUid(),
                        user.getPower(),
                        user.getName(),
                        user.getAvatar(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getDescription()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PostMapping(path = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto,
                                          @CurrentUser UserPrincipal userPrincipal,
                                          Authentication authentication) {
        try {
            if (userPrincipal == null || (!userPrincipal.getAuthorities().contains(Power.ADMIN.getAuthority()) && !userPrincipal.getAuthorities().contains(Power.MANAGER.getAuthority()) && !userPrincipal.getAuthorities().contains(Power.SYSTEM_ADMIN.getAuthority()))) {
                return responseUtil.getForbiddenResponse();
            }
            userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority();
            String email = signUpRequestDto.getEmail();
            String phoneNumber = signUpRequestDto.getPhoneNumber();
//            int role = signUpRequestDto.getRole();
//            if (role < Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId()) {
//                return responseUtil.getForbiddenResponse();
//            }
            if ((email == null || email.isBlank()) && (phoneNumber == null || phoneNumber.isBlank())) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("email and phone number null");
                errorMap.put(DtoField.PARAM_USERNAME, error);
                return responseUtil.getBadRequestResponse(errorMap);
            }
            if (phoneNumber != null && !phoneNumber.isBlank() && !phoneNumber.matches("[^\\s]+")) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("not correct format phone number");
                errorMap.put(DtoField.PARAM_PHONE_NUMBER, error);
                return responseUtil.getBadRequestResponse(errorMap);

            }
            boolean isEmailUsername = true;
            if (userService.isUserExistByEmail(email)) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add(String.format(Constant.FORMAT_EXIST, DtoField.PARAM_EMAIL));
                errorMap.put(DtoField.PARAM_EMAIL, error);
                return responseUtil.getBadRequestResponse(errorMap);
            }

            if (Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId() == Power.ADMIN.getId()
                    || Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId() == Power.SYSTEM_ADMIN.getId()) {
                signUpRequestDto.setPower(Power.MANAGER.getId());
            } else if (Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId() == Power.MANAGER.getId()) {
                signUpRequestDto.setPower(Power.STAFF.getId());
            }

            User user = userService.createNewUser(signUpRequestDto, isEmailUsername);
            return responseUtil.getSuccessResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
