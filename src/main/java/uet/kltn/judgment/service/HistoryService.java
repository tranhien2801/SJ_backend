package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.Precedent;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.constant.Vote;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.judgment.FilterJudgmentRequestDto;
import uet.kltn.judgment.dto.response.judgment.JudgmentResponseDto;
import uet.kltn.judgment.model.History;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.respository.HistoryRepository;
import uet.kltn.judgment.respository.JudgmentLikedRepository;
import uet.kltn.judgment.respository.UserRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JudgmentLikedRepository judgmentLikedRepository;

    public PageDto getJudgmentsViewed(ExpressionDto expressionDto, String userUid, FilterJudgmentRequestDto filterJudgmentRequestDto) {
        try {
            User user = userRepository.findByUid(userUid);
            if (user == null)   return null;
            Page<History> judgmentPage;

            if (filterJudgmentRequestDto == null || filterJudgmentRequestDto.isEmpty()) {
                judgmentPage = historyRepository.findAllByUserAndStateOrderByCreatedDesc(expressionDto.getPageable(), user, State.ACTIVE.getId());
            } else {
                Date dateFrom = (filterJudgmentRequestDto.getDateFrom() != null) ? filterJudgmentRequestDto.getDateFrom() : Date.valueOf("2022-11-01");
                Date dateTo = filterJudgmentRequestDto.getDateTo() != null ? filterJudgmentRequestDto.getDateTo() : Date.valueOf("2022-11-30");
                Integer vote = filterJudgmentRequestDto.getVote() != null ? Vote.IS_VOTED.getId() : Vote.NO_IS_VOTED.getId();
                if (filterJudgmentRequestDto.getPrecedent() == null) {
                    judgmentPage = historyRepository.findByFilterAndState(
                            expressionDto.getPageable(),
                            userUid,
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
                    judgmentPage = historyRepository.findByFilterAndStateAndPrecedent(
                            expressionDto.getPageable(),
                            userUid,
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
            List<JudgmentResponseDto> judgmentResponseDtos = new ArrayList<>();
            judgmentPage.forEach(judgment -> {
                String userLikedUid = null;
                if (judgmentLikedRepository.existsJudgmentLikedByJudgmentAndUserAndState(judgment.getJudgment(), judgment.getUser(), State.ACTIVE.getId())) {
                    userLikedUid = user.getUid();
                }
                judgmentResponseDtos.add(
                        new JudgmentResponseDto(
                                judgment.getJudgment().getUid(),
                                userLikedUid,
                                judgment.getJudgment().getJudgmentNumber(),
                                judgment.getJudgment().getJudgmentName(),
                                judgment.getJudgment().getTypeDocument(),
                                judgment.getJudgment().getJudgmentLevel(),
                                judgment.getJudgment().getCourt() != null ? judgment.getJudgment().getCourt().getCourtName() : null,
                                judgment.getJudgment().getACase() != null ? judgment.getJudgment().getACase().getCaseName() : null,
                                judgment.getJudgment().getACase().getCaseType() != null ? judgment.getJudgment().getACase().getCaseType() : null,
                                judgment.getJudgment().getJudgmentContent(),
//                                judgment.getJudgment().getJudgmentText(),
                                judgment.getJudgment().getJudgmentSummarization(),
                                judgment.getJudgment().getDateIssued(),
                                judgment.getJudgment().getDateUpload(),
                                judgment.getJudgment().getUrl(),
                                judgment.getJudgment().getFileDownload(),
                                judgment.getJudgment().getPdfViewer(),
                                judgment.getJudgment().getCountVote(),
                                judgment.getJudgment().getCountEyes(),
                                judgment.getJudgment().getCountDownload(),
                                judgment.getJudgment().getPrecedent()));
            });
            return new PageDto(judgmentResponseDtos, expressionDto.getPageable().getPageSize(), judgmentPage.getTotalElements(), expressionDto.getPage());
        }   catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
