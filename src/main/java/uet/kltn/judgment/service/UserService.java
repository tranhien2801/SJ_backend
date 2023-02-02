package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uet.kltn.judgment.constant.*;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.auth.SignUpRequestDto;
import uet.kltn.judgment.dto.request.auth.UpdateUserRequestDto;
import uet.kltn.judgment.dto.response.auth.UserResponseDto;
import uet.kltn.judgment.model.CaseType;
import uet.kltn.judgment.model.Function;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.respository.UserRepository;
import uet.kltn.judgment.util.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createNewUser(SignUpRequestDto signUpRequestDto, boolean isEmailUsername) {
        // Creating user's account
        User user = new User(
                Utils.uuid(),
                State.ACTIVE.getId(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getName(),
                LocalDateTime.now(),
                null,
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPhoneNumber(),
                signUpRequestDto.getDescription(),
                null,
                null,
                null,
                signUpRequestDto.getRole(),
                signUpRequestDto.getPower());
        user = userRepository.save(user);
        return user;

    }

    public User updateUser(User currentUser, UpdateUserRequestDto updateUserRequestDto) {
        currentUser.update(updateUserRequestDto);
        userRepository.save(currentUser);
        return currentUser;
    }

    public User getUserById(String id) {
        return userRepository.findUserByUidAndState(id, State.ACTIVE.getId());
    }

    public List<User> getUserByIdsAndPowerGreater(List<String> uids, int power) {
        return userRepository.findUserByUidInAndStateAndPowerGreaterThanEqual(uids, State.ACTIVE.getId(), power);
    }

    public User updatePower(User user) {
        user.setPower(Power.SYSTEM_ADMIN.getId());
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    public User getUserByUsername(String email) {
        return userRepository.findUserByEmailAndState(email, State.ACTIVE.getId());
    }

    public boolean isUserExistByEmail(String email) {
        return userRepository.existsUserByEmailAndState(email, State.ACTIVE.getId());
    }

    public boolean isAnotherUserExistByEmail(String email, String id) {
        return userRepository.existsUserByEmailAndUidNotAndState(email, id, State.ACTIVE.getId());
    }

    public User resetPassword(String password, User user) {
        user.setPassword(passwordEncoder.encode(password));
        user.setModified(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    public PageDto getCurrentUser(ExpressionDto expressionDto, String uid) {
        Page<User> userPage = userRepository.findByUidAndState(expressionDto.getPageable(), uid, State.ACTIVE.getId());
        List<User> users = userPage.getContent();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            userResponseDtos.add(
                    new UserResponseDto(
                            user.getUid(),
                            user.getName(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getDescription(),
                            user.getAvatar(),
                            user.getGender() != null ? Gender.getById(user.getGender()).name() : null,
                            user.getBirthday(),
                            Power.getById(user.getPower()).name(),
                            State.getById(user.getState()).name(),
                            user.getUsageTime() != null ? UsageTime.getById(user.getUsageTime()).name() : null,
                            RoleUser.getById(user.getRole()).name(),
                            user.getUnit() != null ? user.getUnit().getUnitName() : null,
                            user.getWork() != null ? user.getWork().getWorkName() : null,
                            0,
                            user.getCaseTypes().stream().findFirst().orElseThrow().getCaseTypeName(),
                            user.getFunctions().stream().findFirst().orElseThrow().getFunctionName(),
                            user.getLastLogin()));
        });

        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

    public PageDto getAllUser(ExpressionDto expressionDto, int role) {
        List<Integer> roleList = new ArrayList<>();
        for (int i = role; i <= 4 ; i++) {
            roleList.add(i);
        }
        Page<User> userPage = userRepository.findAllByStateAndPowerIn(expressionDto.getPageable(), State.ACTIVE.getId(), roleList);
        List<User> users = userPage.getContent();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            userResponseDtos.add(
                    new UserResponseDto(
                            user.getUid(),
                            user.getName(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getDescription(),
                            user.getAvatar(),
                            user.getGender() != null ? Gender.getById(user.getGender()).name() : null,
                            user.getBirthday(),
                            Power.getById(user.getPower()).name(),
                            State.getById(user.getState()).name(),
                            user.getUsageTime() != null ? UsageTime.getById(user.getUsageTime()).name() : null,
                            RoleUser.getById(user.getRole()).name(),
                            user.getUnit() != null ? user.getUnit().getUnitName() : null,
                            user.getWork() != null ? user.getWork().getWorkName() : null,
                            0,
                            user.getCaseTypes().size() != 0 ? user.getCaseTypes().stream().findFirst().orElseThrow().getCaseTypeName() : null,
                            user.getFunctions().size() != 0 ? user.getFunctions().stream().findFirst().orElseThrow().getFunctionName() : null,
                            user.getLastLogin()));
        });

        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

    public PageDto getUserIsEnterpriseManagement(ExpressionDto expressionDto) {
        Page<User> userPage = userRepository.findByLevelAndRoleAndState(expressionDto.getPageable(), Level.LEVEL_ENTERPRISE.getId(), RoleUser.ROLE_MANAGER.getId(), State.ACTIVE.getId());
        List<User> users = userPage.getContent();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(user -> {
            userResponseDtos.add(
                    new UserResponseDto(
                            user.getUid(),
                            user.getName(),
                            user.getEmail(),
                            user.getPhoneNumber(),
                            user.getDescription(),
                            user.getAvatar(),
                            user.getGender() != null ? Gender.getById(user.getGender()).name() : null,
                            user.getBirthday(),
                            Power.getById(user.getPower()).name(),
                            State.getById(user.getState()).name(),
                            user.getUsageTime() != null ? UsageTime.getById(user.getUsageTime()).name() : null,
                            RoleUser.getById(user.getRole()).name(),
                            user.getUnit() != null ? user.getUnit().getUnitName() : null,
                            user.getWork() != null ? user.getWork().getWorkName() : null,
                            0,
                            user.getCaseTypes().size() != 0 ? user.getCaseTypes().stream().findFirst().orElseThrow().getCaseTypeName() : null,
                            user.getFunctions().size() != 0 ? user.getFunctions().stream().findFirst().orElseThrow().getFunctionName() : null,
                            user.getLastLogin()));
        });
        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

//    public PageDto getUsersByFilter(ExpressionDto expressionDto, int role) {
//        List<Integer> roleList = new ArrayList<>();
//        for (int i = role; i <= 4 ; i++) {
//            roleList.add(i);
//        }
//        Page<User> userPage = userRepository.findUsersByStateAndPowerIn(expressionDto.getPageable(), State.ACTIVE.getId(), roleList);
//        List<User> users = userPage.getContent();
//        List<UserResponseDto> userResponseDtos = new ArrayList<>();
//        users.forEach(user -> {
//            userResponseDtos.add(
//                    new UserResponseDto(
//                            user.getUid(),
//                            user.getName(),
//                            user.getEmail(),
//                            user.getPhoneNumber(),
//                            user.getDescription(),
//                            user.getAvatar(),
//                            user.getGender(),
//                            user.getBirthday(),
//                            user.getPower(),
//                            user.getLastLogin()));
//        });
//
//        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
//    }

    public void deleteListUser(List<User> users) {
        users.forEach(user -> {
            user.setState(State.DELETE.getId());
            user.setModified(LocalDateTime.now());
        });
        userRepository.saveAll(users);
    }
}
