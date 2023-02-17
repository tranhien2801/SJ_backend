package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.common.ExpressionDto;
import uet.kltn.judgment.dto.request.judgment.FilterJudgmentRequestDto;
import uet.kltn.judgment.dto.request.judgment.UpdateJudgmentRequestDto;
import uet.kltn.judgment.dto.response.judgment.JudgmentResponseDto;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.respository.JudgmentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class JudgmentService {
    @Autowired
    private JudgmentRepository judgmentRepository;

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
        return new JudgmentResponseDto(
                judgment.getUid(),
                judgment.getJudgmentNumber(),
                judgment.getJudgmentName(),
                judgment.getTypeDocument(),
                judgment.getJudgmentLevel(),
                judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                judgment.getJudgmentContent(),
                judgment.getJudgmentText(),
                judgment.getDateIssued(),
                judgment.getDateUpload(),
                judgment.getUrl(),
                judgment.getFileDownload(),
                judgment.getPdfViewer(),
                judgment.getCountVote(),
                judgment.getCountEyes(),
                judgment.getCountDownload());
    }

    public List<Judgment> getJudgmentByUids(List<String> uids) {
        return judgmentRepository.findByUidInAndState(uids, State.ACTIVE.getId());
    }

    public PageDto getJudgmentsByFilter(ExpressionDto expressionDto, FilterJudgmentRequestDto filterJudgmentRequestDto) {
        Page<Judgment> judgmentPage;
        if (filterJudgmentRequestDto == null || filterJudgmentRequestDto.isEmpty()) {
            judgmentPage = judgmentRepository.findAllByState(expressionDto.getPageable(), State.ACTIVE.getId());
        } else {
            judgmentPage = judgmentRepository.findByFilterAndState(
                    expressionDto.getPageable(),
                    filterJudgmentRequestDto.getJudgmentNumber() != null ? filterJudgmentRequestDto.getJudgmentNumber() : "",
                    filterJudgmentRequestDto.getCourtLevel() != null ? filterJudgmentRequestDto.getCourtLevel() : "",
                    filterJudgmentRequestDto.getJudgmentLevel() != null ? filterJudgmentRequestDto.getJudgmentLevel() : "",
                    filterJudgmentRequestDto.getTypeDocument() != null ? filterJudgmentRequestDto.getTypeDocument() : "",
                    filterJudgmentRequestDto.getCaseType() != null ? filterJudgmentRequestDto.getCaseType() : "",
                    State.ACTIVE.getId());
        }
        List<Judgment> judgments = judgmentPage.getContent();
        List<JudgmentResponseDto> judgmentResponseDtos = new ArrayList<>();
        judgments.forEach(judgment -> {
            judgmentResponseDtos.add(
                    new JudgmentResponseDto(
                            judgment.getUid(),
                            judgment.getJudgmentNumber(),
                            judgment.getJudgmentName(),
                            judgment.getTypeDocument(),
                            judgment.getJudgmentLevel(),
                            judgment.getCourt() != null ? judgment.getCourt().getCourtName() : null,
                            judgment.getACase() != null ? judgment.getACase().getCaseName() : null,
                            judgment.getACase().getCaseType() != null ? judgment.getACase().getCaseType() : null,
                            judgment.getJudgmentContent(),
                            judgment.getJudgmentText(),
                            judgment.getDateIssued(),
                            judgment.getDateUpload(),
                            judgment.getUrl(),
                            judgment.getFileDownload(),
                            judgment.getPdfViewer(),
                            judgment.getCountVote(),
                            judgment.getCountEyes(),
                            judgment.getCountDownload()));
        });
        return new PageDto(judgmentResponseDtos, expressionDto.getPageable().getPageSize(), judgmentPage.getTotalElements(), expressionDto.getPage());
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
}
