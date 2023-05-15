package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.*;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.judgment.FilterJudgmentRequestDto;
import uet.kltn.judgment.dto.request.judgment.UserJudgmentRequestDto;
import uet.kltn.judgment.dto.request.judgment.UpdateJudgmentRequestDto;
import uet.kltn.judgment.dto.response.judgment.DataResponseDto;
import uet.kltn.judgment.dto.response.judgment.JudgmentResponseDto;
import uet.kltn.judgment.model.History;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.JudgmentLiked;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.respository.HistoryRepository;
import uet.kltn.judgment.respository.JudgmentLikedRepository;
import uet.kltn.judgment.respository.JudgmentRepository;
import uet.kltn.judgment.respository.UserRepository;


import java.time.LocalDateTime;
import java.util.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class JudgmentService {
    @Autowired
    private JudgmentRepository judgmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private JudgmentLikedRepository judgmentLikedRepository;


    public Judgment updateJudgment(Judgment currentJudgment, UpdateJudgmentRequestDto updateJudgmentRequestDto) {
        currentJudgment.update(updateJudgmentRequestDto);
        judgmentRepository.save(currentJudgment);
        return currentJudgment;
    }

    public Judgment getJudgmentByUid(String uid) {
        return judgmentRepository.findByUid(uid);
    }

    public JudgmentResponseDto getJudgmentResponseDtoByUid(String uid) {
        if (uid == null) return null;
        Judgment judgment =  judgmentRepository.findByUid(uid);
        String userUid = null;
        if (judgment.getUsers().size() != 0) {
            User user = judgment.getUsers().stream().findFirst().orElseThrow();
            if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                userUid = user.getUid();
            }
        }
        return new JudgmentResponseDto(
                judgment.getUid(),
                userUid,
                judgment.getJudgmentNumber(),
                judgment.getJudgmentName(),
                judgment.getTypeDocument(),
                judgment.getJudgmentLevel(),
                judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                judgment.getJudgmentContent(),
//                judgment.getJudgmentText(),
                judgment.getJudgmentSummarization(),
                judgment.getDateIssued(),
                judgment.getDateUpload(),
                judgment.getUrl(),
                judgment.getFileDownload(),
                judgment.getPdfViewer(),
                judgment.getCountVote(),
                judgment.getCountEyes(),
                judgment.getCountDownload(),
                judgment.getPrecedent());
    }

    public List<Judgment> getJudgmentByUids(List<String> uids) {
        return judgmentRepository.findByUidInAndState(uids, State.ACTIVE.getId());
    }

    public PageDto getJudgmentsByFilter(ExpressionDto expressionDto, FilterJudgmentRequestDto filterJudgmentRequestDto) {
        try {
            Page<Judgment> judgmentPage;
            if (filterJudgmentRequestDto == null || filterJudgmentRequestDto.isEmpty()) {
                judgmentPage = judgmentRepository.findAllByStateOrderByDateIssuedDesc(expressionDto.getPageable(), State.ACTIVE.getId());
            } else {
                Date dateFrom = (filterJudgmentRequestDto.getDateFrom() != null) ? filterJudgmentRequestDto.getDateFrom() : Date.valueOf("2022-11-01");
                Date dateTo = filterJudgmentRequestDto.getDateTo() != null ? filterJudgmentRequestDto.getDateTo() : Date.valueOf("2022-11-30");
                Integer vote = filterJudgmentRequestDto.getVote() != null ? Vote.IS_VOTED.getId() : Vote.NO_IS_VOTED.getId();
                if (filterJudgmentRequestDto.getPrecedent() == null) {
                    judgmentPage = judgmentRepository.findByFilterAndState(
                            expressionDto.getPageable(),
                            filterJudgmentRequestDto.getJudgmentNumber() != null ? filterJudgmentRequestDto.getJudgmentNumber() : "",
                            filterJudgmentRequestDto.getCourtLevel() != null ? filterJudgmentRequestDto.getCourtLevel() : "",
                            filterJudgmentRequestDto.getJudgmentLevel() != null ? filterJudgmentRequestDto.getJudgmentLevel() : "",
                            filterJudgmentRequestDto.getTypeDocument() != null ? filterJudgmentRequestDto.getTypeDocument() : "",
                            filterJudgmentRequestDto.getCaseType() != null ? filterJudgmentRequestDto.getCaseType() : "",
                            dateFrom,
                            dateTo,
                            vote,
                            State.ACTIVE.getId());
                } else {
                    judgmentPage = judgmentRepository.findByFilterAndStateAndPrecedent(
                            expressionDto.getPageable(),
                            filterJudgmentRequestDto.getJudgmentNumber() != null ? filterJudgmentRequestDto.getJudgmentNumber() : "",
                            filterJudgmentRequestDto.getCourtLevel() != null ? filterJudgmentRequestDto.getCourtLevel() : "",
                            filterJudgmentRequestDto.getJudgmentLevel() != null ? filterJudgmentRequestDto.getJudgmentLevel() : "",
                            filterJudgmentRequestDto.getTypeDocument() != null ? filterJudgmentRequestDto.getTypeDocument() : "",
                            filterJudgmentRequestDto.getCaseType() != null ? filterJudgmentRequestDto.getCaseType() : "",
                            dateFrom,
                            dateTo,
                            vote,
                            filterJudgmentRequestDto.getPrecedent() == true ? Precedent.APPLYING_PRECEDENT.getId() : Precedent.NO_APPLYING_PRECEDENT.getId(),
                            State.ACTIVE.getId());
                }
            }
            List<Judgment> judgments = judgmentPage.getContent();
            List<JudgmentResponseDto> judgmentResponseDtos = new ArrayList<>();
            judgments.forEach(judgment -> {
                String userUid = null;
                if (judgment.getUsers().size() != 0) {
                    User user = judgment.getUsers().stream().findFirst().orElseThrow();
                    if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                        userUid = user.getUid();
                    }
                }
                judgmentResponseDtos.add(
                        new JudgmentResponseDto(
                                judgment.getUid(),
                                userUid,
                                judgment.getJudgmentNumber(),
                                judgment.getJudgmentName(),
                                judgment.getTypeDocument(),
                                judgment.getJudgmentLevel(),
                                judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                                judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                                judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                                judgment.getJudgmentContent(),
//                                judgment.getJudgmentText(),
                                judgment.getJudgmentSummarization(),
                                judgment.getDateIssued(),
                                judgment.getDateUpload(),
                                judgment.getUrl(),
                                judgment.getFileDownload(),
                                judgment.getPdfViewer(),
                                judgment.getCountVote(),
                                judgment.getCountEyes(),
                                judgment.getCountDownload(),
                                judgment.getPrecedent()));
            });
            return new PageDto(judgmentResponseDtos, expressionDto.getPageable().getPageSize(), judgmentPage.getTotalElements(), expressionDto.getPage());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void deleteListJudgment(List<Judgment> judgments) {
        judgments.forEach(judgment -> {
            judgment.setState(State.DELETE.getId());
            judgment.setModified(LocalDateTime.now());
        });
        judgmentRepository.saveAll(judgments);
    }

    public Set<String> getJudgmentLevels() {
        return judgmentRepository.findJudgmentLevels();
    }

    public boolean historyJudgment(UserJudgmentRequestDto userJudgmentRequestDto) {
        User user = userRepository.findByUid(userJudgmentRequestDto.getUserUid());
        if (user == null) return false;
        Judgment judgment = judgmentRepository.findByUid(userJudgmentRequestDto.getJudgmentUid());
        if (judgment == null) return false;
        try {
            if (!historyRepository.existsHistoriesByJudgmentAndUser(judgment, user)) {
                History history = new History(user, judgment);
                historyRepository.save(history);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean userLikedJudgment(UserJudgmentRequestDto likedJudgmentRequestDto) {
        User user = userRepository.findByUid(likedJudgmentRequestDto.getUserUid());
        if (user == null) return false;
        Judgment judgment = judgmentRepository.findByUid(likedJudgmentRequestDto.getJudgmentUid());
        if (judgment == null) return false;
        try {
            JudgmentLiked judgmentLiked;
            if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                judgmentLiked = judgmentLikedRepository.findByUserAndJudgmentAndState(user, judgment, State.ACTIVE.getId());
                judgmentLiked.setState(State.INACTIVE.getId());
                judgmentLiked.setModified(LocalDateTime.now());
            } else if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.INACTIVE.getId())) {
                judgmentLiked = judgmentLikedRepository.findByUserAndJudgmentAndState(user, judgment, State.INACTIVE.getId());
                judgmentLiked.setState(State.ACTIVE.getId());
                judgmentLiked.setModified(LocalDateTime.now());
            } else {
                judgmentLiked = new JudgmentLiked(user, judgment);
            }
            judgmentLikedRepository.save(judgmentLiked);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public JudgmentResponseDto viewJudgmentDetail(String uid) {
        try {
            Judgment judgment = judgmentRepository.findByUid(uid);
            judgment.setCountEyes(judgment.getCountEyes()+1);
            judgmentRepository.save(judgment);
            String userUid = null;
            if (judgment.getUsers().size() != 0) {
                User user = judgment.getUsers().stream().findFirst().orElseThrow();
                if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                    userUid = user.getUid();
                }
            }
            return new JudgmentResponseDto(
                    judgment.getUid(),
                    userUid,
                    judgment.getJudgmentNumber(),
                    judgment.getJudgmentName(),
                    judgment.getTypeDocument(),
                    judgment.getJudgmentLevel(),
                    judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                    judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                    judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                    judgment.getJudgmentContent(),
//                    judgment.getJudgmentText(),
                    judgment.getJudgmentSummarization(),
                    judgment.getDateIssued(),
                    judgment.getDateUpload(),
                    judgment.getUrl(),
                    judgment.getFileDownload(),
                    judgment.getPdfViewer(),
                    judgment.getCountVote(),
                    judgment.getCountEyes(),
                    judgment.getCountDownload(),
                    judgment.getPrecedent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JudgmentResponseDto downloadJudgment(String uid) {
        try {
            Judgment judgment = judgmentRepository.findByUid(uid);
            judgment.setCountDownload(judgment.getCountDownload()+1);
            judgmentRepository.save(judgment);
            String userUid = null;
            if (judgment.getUsers().size() != 0) {
                User user = judgment.getUsers().stream().findFirst().orElseThrow();
                if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                    userUid = user.getUid();
                }
            }
            return new JudgmentResponseDto(
                    judgment.getUid(),
                    userUid,
                    judgment.getJudgmentNumber(),
                    judgment.getJudgmentName(),
                    judgment.getTypeDocument(),
                    judgment.getJudgmentLevel(),
                    judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                    judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                    judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                    judgment.getJudgmentContent(),
//                    judgment.getJudgmentText(),
                    judgment.getJudgmentSummarization(),
                    judgment.getDateIssued(),
                    judgment.getDateUpload(),
                    judgment.getUrl(),
                    judgment.getFileDownload(),
                    judgment.getPdfViewer(),
                    judgment.getCountVote(),
                    judgment.getCountEyes(),
                    judgment.getCountDownload(),
                    judgment.getPrecedent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JudgmentResponseDto voteJudgment(String judgmentUid) {
        try {
            Judgment judgment = judgmentRepository.findByUid(judgmentUid);
            if (judgment == null)   return null;
            judgment.setCountVote(judgment.getCountVote()+1);
            judgmentRepository.save(judgment);
            String userUid = null;
            if (judgment.getUsers().size() != 0) {
                User user = judgment.getUsers().stream().findFirst().orElseThrow();
                if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment, user, State.ACTIVE.getId())) {
                    userUid = user.getUid();
                }
            }
            return new JudgmentResponseDto(
                    judgment.getUid(),
                    userUid,
                    judgment.getJudgmentNumber(),
                    judgment.getJudgmentName(),
                    judgment.getTypeDocument(),
                    judgment.getJudgmentLevel(),
                    judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                    judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                    judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                    judgment.getJudgmentContent(),
//                    judgment.getJudgmentText(),
                    judgment.getJudgmentSummarization(),
                    judgment.getDateIssued(),
                    judgment.getDateUpload(),
                    judgment.getUrl(),
                    judgment.getFileDownload(),
                    judgment.getPdfViewer(),
                    judgment.getCountVote(),
                    judgment.getCountEyes(),
                    judgment.getCountDownload(),
                    judgment.getPrecedent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DataResponseDto getDataForDashBoard() {
        try {
            Integer totalJudgments = judgmentRepository.countAllByState(State.ACTIVE.getId());
            Integer totalUsers = userRepository.countAllBy() - 1;
            Integer totalManagers = userRepository.countAllByLevelAndRole(Level.LEVEL_ENTERPRISE.getId(), RoleUser.ROLE_MANAGER.getId());
            Date dateLastest = judgmentRepository.minDateIssued();
            Date dateNewest = judgmentRepository.maxDateIssued();
            return new DataResponseDto(totalJudgments, totalUsers, totalManagers, dateLastest, dateNewest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<?> countByCaseType() {
        return judgmentRepository.countByCaseType();
    }
}
