package uet.kltn.judgment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.constant.DtoField;
import uet.kltn.judgment.constant.Power;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.auth.ChangePasswordRequestDto;
import uet.kltn.judgment.dto.request.auth.ResetPasswordRequestDto;
import uet.kltn.judgment.dto.request.user.UpdateUserRequestDto;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.security.CurrentUser;
import uet.kltn.judgment.security.UserPrincipal;
import uet.kltn.judgment.service.UserService;
import uet.kltn.judgment.service.email.EmailService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends GenController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public UserController() {

    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                            @CurrentUser UserPrincipal userPrincipal,
                                            Authentication authentication) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }

            User currentUser = userPrincipal.getUser();
            if (!BCrypt.checkpw(changePasswordRequestDto.getCurrentPassword(), currentUser.getPassword())) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("Mật khẩu hiện tại không đúng");
                errorMap.put(DtoField.PARAM_PASSWORD, error);
                return responseUtil.getUnauthorizedResponse(errorMap);
            }

            String newPassword = changePasswordRequestDto.getNewPassword();
            String reNewPassword = changePasswordRequestDto.getReNewPassword();
            if (!newPassword.equals(reNewPassword)) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("Mật khẩu mới và mật khẩu xác nhận không trùng khớp");
                errorMap.put(DtoField.PARAM_PASSWORD, error);
                return responseUtil.getBadRequestResponse(errorMap);
            }

            userService.resetPassword(newPassword, userPrincipal.getUser());

            return responseUtil.getSuccessResponse();
        } catch (Exception e) {
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") String id, @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto,
                                        @CurrentUser UserPrincipal userPrincipal,
                                        Authentication authentication) {
        try {
            if (userPrincipal == null ||
                    (userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()) && !userPrincipal.getId().equals(id))) {
                return responseUtil.getForbiddenResponse();
            }

            User user = userService.getUserById(id);
            if (user == null) {
                return responseUtil.getNotFoundResponse(id);
            }

            if ((user.getPower() < Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId())) {
                return responseUtil.getForbiddenResponse();
            }

            String email = updateUserRequestDto.getEmail();
            String phoneNumber = updateUserRequestDto.getPhoneNumber();

            if (phoneNumber != null && !phoneNumber.isBlank() && !phoneNumber.matches("[^\\s]+")) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add("Số điện thoại không đúng định dạng");
                errorMap.put(DtoField.PARAM_PHONE_NUMBER, error);
                return responseUtil.getBadRequestResponse(errorMap);
            }

            if (userService.isAnotherUserExistByEmail(email, id)) {
                Map<String, List<String>> errorMap = new HashMap<>();
                List<String> error = new ArrayList<>();
                error.add(String.format(Constant.FORMAT_EXIST, DtoField.PARAM_EMAIL));
                errorMap.put(DtoField.PARAM_EMAIL, error);
                return responseUtil.getBadRequestResponse(errorMap);
            }
            user = userService.updateUser(user, updateUserRequestDto);
            return responseUtil.getSuccessResponse(user);

        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable(value = "id") String id, @Valid @RequestBody ResetPasswordRequestDto resetPasswordRequestDto,
                                           @CurrentUser UserPrincipal userPrincipal,
                                           Authentication authentication) {
        try {
            if (userPrincipal == null ||
                    (userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()) && !userPrincipal.getId().equals(id))) {
                return responseUtil.getForbiddenResponse();
            }

            User user = userService.getUserByIdAndState(id);
            if (user == null) {
                return responseUtil.getNotFoundResponse(id);
            }

            if ((user.getPower() < Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId())) {
                return responseUtil.getForbiddenResponse();
            }
            user = userService.resetPassword(resetPasswordRequestDto.getPassword(), user);
            return responseUtil.getSuccessResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }

    }

    @GetMapping("/list")
    public ResponseEntity<?> getUsersList(@CurrentUser UserPrincipal userPrincipal,
                                          Authentication authentication,
                                          @RequestParam Map<String, Object> request) {
        try {
            if (userPrincipal == null) {
                return responseUtil.getForbiddenResponse();
            }
            PageDto response = new PageDto();
            Map<String, Object> params = utils.getExpressionAndParams(request, User.class);
            ExpressionDto expressionDto = (ExpressionDto) params.get("expression");
            if ((userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()))) {
                response = userService.getCurrentUser(expressionDto, userPrincipal.getUser().getUid());
            } else {
                if (userPrincipal.getAuthorities().contains(Power.SYSTEM_ADMIN.getAuthority()))
                    response = userService.getAllUser(expressionDto, Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId(), userPrincipal.getId());
                else if (userPrincipal.getAuthorities().contains(Power.MANAGER.getAuthority()))
                    response = userService.getAllUserByUnit(expressionDto, userPrincipal.getId());
            }
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping(path = "/managers")
    public ResponseEntity<?> getListManager(@CurrentUser UserPrincipal userPrincipal,
                                            Authentication authentication,
                                            @RequestParam Map<String, Object> request) {
        try {
            if (userPrincipal == null
                    || userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority())
                    || userPrincipal.getAuthorities().contains(Power.MANAGER.getAuthority())) {
                return responseUtil.getForbiddenResponse();
            }
            PageDto response = new PageDto();
            Map<String, Object> params = utils.getExpressionAndParams(request, User.class);
            ExpressionDto expressionDto = (ExpressionDto) params.get("expression");
            response = userService.getUserIsEnterpriseManagement(expressionDto);
            return responseUtil.getSuccessResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "id") String id,
                                           @CurrentUser UserPrincipal userPrincipal,
                                           Authentication authentication) {
        try {
            if (userPrincipal == null ) {
                return responseUtil.getForbiddenResponse();
            }

            User user = userService.getUserByIdAndState(id);
            if (user == null) {
                return responseUtil.getNotFoundResponse(id);
            }

            return responseUtil.getSuccessResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }

    }

    @DeleteMapping()
    public ResponseEntity<?> deleteUser(@Valid @RequestParam(name = "uids") List<String> uids,
                                        @CurrentUser UserPrincipal userPrincipal,
                                        Authentication authentication) {
        try {
            if (userPrincipal == null || (userPrincipal.getAuthorities().contains(Power.STAFF.getAuthority()))) {
                return responseUtil.getForbiddenResponse();
            }

            int powerCurrentUser = Power.valueOf(userPrincipal.getAuthorities().stream().findFirst().orElseThrow().getAuthority()).getId();

            List<User> users = userService.getUserByIdsAndPowerGreater(uids, powerCurrentUser);
            if (users.size() != uids.size()) {
                return responseUtil.getBadRequestResponse("Not found all uids or your role is lower than some uids");
            }

            userService.deleteListUser(users);
            return responseUtil.getSuccessResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }

    @GetMapping("/confirm-signup/{id}")
    public ResponseEntity<?> confirmSignup(@PathVariable(value = "id") String id) {
        try {
            User user = userService.updateState(id);
            if (user == null) {
                return responseUtil.getBadRequestResponse("Không tìm thấy người dùng trong hệ thống");
            }
            if (userService.isSevenDaysTrial(user.getUid())) {
                System.out.println("Gửi thư thông báo thời hạn sử dụng tài khoản Trial");
                emailService.sendNotiSevenDaysTrial(user);
            } else {
                System.out.println("Không gửi email");
            }
            return responseUtil.getSuccessResponse("Kích hoạt tài khoản thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            return responseUtil.getInternalServerErrorResponse();
        }
    }
}
