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
import uet.kltn.judgment.dto.request.user.SignUpRequestDto;
import uet.kltn.judgment.dto.request.user.UpdateUserRequestDto;
import uet.kltn.judgment.dto.response.auth.UserResponseDto;
import uet.kltn.judgment.model.*;
import uet.kltn.judgment.respository.*;
import uet.kltn.judgment.util.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FunctionRepository functionRespository;

    @Autowired
    private CaseTypeRepository caseTypeRespository;

    @Autowired
    private WorkRepository workRespository;

    @Autowired
    private UnitRepository unitRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createNewUser(SignUpRequestDto signUpRequestDto, boolean isEmailUsername) {
        // Creating user's account
        User user = new User(Utils.uuid(),
                            passwordEncoder.encode(signUpRequestDto.getPassword()),
                            signUpRequestDto);
        if (signUpRequestDto.getFunctions() != null) {
            Set<Function> functions = functionRespository.findByFunctionNameInAndState(signUpRequestDto.getFunctions(), State.ACTIVE.getId());
            user.setFunctions(functions);
        }

        if (signUpRequestDto.getCaseTypes() != null) {
            Set<CaseType> caseTypes = caseTypeRespository.findByCaseTypeNameInAndState(signUpRequestDto.getCaseTypes(), State.ACTIVE.getId());
            user.setCaseTypes(caseTypes);
        }

        if (signUpRequestDto.getWork() != null) {
            Work work = workRespository.findByWorkNameAndState(signUpRequestDto.getWork(), State.ACTIVE.getId());
            if (work == null) work = new Work(Utils.uuid(), signUpRequestDto.getWork());
            workRespository.save(work);
            user.setWork(work);
        }

        if (signUpRequestDto.getUnit() != null) {
            Unit unit = unitRespository.findByUnitNameAndState(signUpRequestDto.getUnit(), State.ACTIVE.getId());
            user.setUnit(unit);
        }
        if (signUpRequestDto.getRole() != null) {
            user.setRole(RoleUser.getByName(signUpRequestDto.getRole()).getId());
        }
        user.setState(State.INACTIVE.getId());
        user = userRepository.save(user);
        return user;

    }

    public User updateUser(User currentUser, UpdateUserRequestDto updateUserRequestDto) {
        if (updateUserRequestDto.getName() != null && (currentUser.getName() == null || (currentUser.getName() != null && !currentUser.getName().equals(updateUserRequestDto.getName())))) {
            currentUser.setName(updateUserRequestDto.getName());
        }
        if (updateUserRequestDto.getEmail() != null && !currentUser.getEmail().equals(updateUserRequestDto.getEmail())) {
            currentUser.setEmail(updateUserRequestDto.getEmail());
        }
        if (updateUserRequestDto.getLevel() != null && (currentUser.getLevel() == null || (currentUser.getLevel() != null && !Level.getById(currentUser.getLevel()).getName().equals(updateUserRequestDto.getLevel())))) {
            currentUser.setLevel(Level.getByName(updateUserRequestDto.getLevel()).getId());
        }
        if (updateUserRequestDto.getState() != null && !State.getById(currentUser.getState()).equals(updateUserRequestDto.getState())) {
            currentUser.setState(State.getByName(updateUserRequestDto.getState()).getId());
        }
        if (updateUserRequestDto.getUsageTime() != null && (currentUser.getUsageTime() == null || (currentUser.getUsageTime() != null && !UsageTime.getById(currentUser.getUsageTime()).equals(updateUserRequestDto.getUsageTime())))) {
            currentUser.setUsageTime(UsageTime.getByName(updateUserRequestDto.getUsageTime()).getId());
        }
        if (updateUserRequestDto.getFunctions() != null && (currentUser.getFunctions() == null || (currentUser.getFunctions() != null && !currentUser.getFunctions().equals(updateUserRequestDto.getFunctions())))) {
            Set<Function> functions = functionRespository.findByFunctionNameInAndState(updateUserRequestDto.getFunctions(), State.ACTIVE.getId());
            currentUser.setFunctions(functions);
        }
        if (updateUserRequestDto.getCaseTypes() != null && (currentUser.getCaseTypes() == null || (currentUser.getCaseTypes() != null && !currentUser.getCaseTypes().equals(updateUserRequestDto.getCaseTypes())))) {
            Set<CaseType> caseTypes = caseTypeRespository.findByCaseTypeNameInAndState(updateUserRequestDto.getCaseTypes(), State.ACTIVE.getId());
            currentUser.setCaseTypes(caseTypes);
        }
        if (updateUserRequestDto.getPhoneNumber() != null && ( currentUser.getPhoneNumber() == null || (currentUser.getPhoneNumber() != null && !currentUser.getPhoneNumber().equals(updateUserRequestDto.getPhoneNumber())))) {
            currentUser.setPhoneNumber(updateUserRequestDto.getPhoneNumber());
        }
        if (updateUserRequestDto.getRole() != null && (currentUser.getRole() == null || (currentUser.getRole() != null && !RoleUser.getById(currentUser.getRole()).equals(updateUserRequestDto.getRole())))) {
            currentUser.setRole(RoleUser.getByName(updateUserRequestDto.getRole()).getId());
        }
        if (updateUserRequestDto.getWork() != null && (currentUser.getWork() == null || (currentUser.getWork() != null && !currentUser.getWork().equals(updateUserRequestDto.getWork())))) {
            Work work = workRespository.findByWorkNameAndState(updateUserRequestDto.getWork(), State.ACTIVE.getId());
            if (work == null) work = new Work(Utils.uuid(), updateUserRequestDto.getWork());
            workRespository.save(work);
            currentUser.setWork(work);
        }
        if (updateUserRequestDto.getUnit() != null && (currentUser.getUnit() == null || (currentUser.getUnit() != null && !currentUser.getUnit().equals(updateUserRequestDto.getUnit())))) {
            Unit unit = unitRespository.findByUnitNameAndState(updateUserRequestDto.getUnit(), State.ACTIVE.getId());
            currentUser.setUnit(unit);
        }
        if (updateUserRequestDto.getNumberEmployee() != null && (currentUser.getNumberEmployee() == null || (currentUser.getNumberEmployee() != null && currentUser.getNumberEmployee() != updateUserRequestDto.getNumberEmployee()))) {
            currentUser.setNumberEmployee(updateUserRequestDto.getNumberEmployee());
        }
        if (updateUserRequestDto.getDescription() != null && (currentUser.getDescription() == null || (currentUser.getDescription() != null && !currentUser.getDescription().equals(updateUserRequestDto.getDescription())))) {
            currentUser.setDescription(updateUserRequestDto.getDescription());
        }
        userRepository.save(currentUser);
        return currentUser;
    }

    public User updateState(String uid) {
        User userRepo = userRepository.findUserByUidAndState(uid, State.INACTIVE.getId());
        if (userRepo != null) {
            userRepo.setState(State.ACTIVE.getId());
            userRepo.setModified(LocalDateTime.now());
            userRepository.save(userRepo);
            return userRepo;
        }
        return null;
    }

    public User getUserById(String id) {
        return userRepository.findByUid(id);
    }

    public User getUserByIdAndState(String id) {
        return userRepository.findUserByUidAndState(id, State.ACTIVE.getId());
    }

    public List<User> getUserByIdsAndPowerGreater(List<String> uids, int power) {
        return userRepository.findUserByUidInAndPowerGreaterThanEqual(uids, power);
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
        return userRepository.existsUserByEmail(email);
    }

    public boolean isAnotherUserExistByEmail(String email, String id) {
        return userRepository.existsUserByEmailAndUidNot(email, id);
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
                            user.getLastLogin(),
                            user.getLevel() != null ? Level.getById(user.getLevel()).getName() : null));
        });

        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

    public PageDto getAllUser(ExpressionDto expressionDto, int role, String uid) {
        List<Integer> roleList = new ArrayList<>();
        for (int i = role; i <= 4; i++) {
            roleList.add(i);
        }
        Page<User> userPage = userRepository.findAllByPowerInAndUidNot(expressionDto.getPageable(), roleList, uid);
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
                            user.getGender() != null ? Gender.getById(user.getGender()).getName() : null,
                            user.getBirthday(),
                            Power.getById(user.getPower()).getName(),
                            State.getById(user.getState()).getName(),
                            user.getUsageTime() != null ? UsageTime.getById(user.getUsageTime()).getName() : null,
                            user.getRole() != null ?  RoleUser.getById(user.getRole()).getName() : null,
                            user.getUnit() != null ? user.getUnit().getUnitName() : null,
                            user.getWork() != null ? user.getWork().getWorkName() : null,
                            0,
                            user.getCaseTypes().size() != 0 ? user.getCaseTypes().stream().findFirst().orElseThrow().getCaseTypeName() : null,
                            user.getFunctions().size() != 0 ? user.getFunctions().stream().findFirst().orElseThrow().getFunctionName() : null,
                            user.getLastLogin(),
                            user.getLevel() != null ? Level.getById(user.getLevel()).getName() : null
                            ));
        });

        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

    public PageDto getAllUserByUnit(ExpressionDto expressionDto, String uid) {
        User manager = userRepository.findByUid(uid);
        if (manager == null || manager.getUnit() == null)   return null;
        Page<User> userPage = userRepository.findAllByUnitAndUidNot(expressionDto.getPageable(), manager.getUnit(), uid);
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
                            user.getGender() != null ? Gender.getById(user.getGender()).getName() : null,
                            user.getBirthday(),
                            Power.getById(user.getPower()).getName(),
                            State.getById(user.getState()).getName(),
                            user.getUsageTime() != null ? UsageTime.getById(user.getUsageTime()).getName() : null,
                            user.getRole() != null ?  RoleUser.getById(user.getRole()).getName() : null,
                            user.getUnit() != null ? user.getUnit().getUnitName() : null,
                            user.getWork() != null ? user.getWork().getWorkName() : null,
                            0,
                            user.getCaseTypes().size() != 0 ? user.getCaseTypes().stream().findFirst().orElseThrow().getCaseTypeName() : null,
                            user.getFunctions().size() != 0 ? user.getFunctions().stream().findFirst().orElseThrow().getFunctionName() : null,
                            user.getLastLogin(),
                            user.getLevel() != null ? Level.getById(user.getLevel()).getName() : null
                    ));
        });

        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }


    public PageDto getUserIsEnterpriseManagement(ExpressionDto expressionDto) {
        Page<User> userPage = userRepository.findByLevelAndRole(expressionDto.getPageable(), Level.LEVEL_ENTERPRISE.getId(), RoleUser.ROLE_MANAGER.getId());
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
                            user.getLastLogin(),
                            user.getLevel() != null ? Level.getById(user.getLevel()).getName() : null
                    ));
        });
        return new PageDto(userResponseDtos, expressionDto.getPageable().getPageSize(), userPage.getTotalElements(), expressionDto.getPage());
    }

    public void deleteListUser(List<User> users) {
        users.forEach(user -> {
            user.setState(State.DELETE.getId());
            user.setModified(LocalDateTime.now());
        });
        userRepository.saveAll(users);
    }

    public boolean isSevenDaysTrial(String uid) {
        User user = userRepository.findUserByUidAndUsageTimeAndState(uid, UsageTime.SEVEN_DAYS_TRIAL.getId(), State.ACTIVE.getId());
        if (user != null) return true;
        else return false;
    }

}
