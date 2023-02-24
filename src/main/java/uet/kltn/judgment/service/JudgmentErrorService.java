package uet.kltn.judgment.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.kltn.judgment.constant.State;
import uet.kltn.judgment.dto.request.judgment.JudgmentErrorRequestDto;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.JudgmentError;
import uet.kltn.judgment.model.User;
import uet.kltn.judgment.respository.JudgmentErrorRepository;
import uet.kltn.judgment.respository.JudgmentRepository;
import uet.kltn.judgment.respository.UserRepository;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class JudgmentErrorService {

    @Autowired
    private JudgmentErrorRepository judgmentErrorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JudgmentRepository judgmentRepository;

    public JudgmentError createJudgmentError(JudgmentErrorRequestDto judgmentErrorRequestDto) {

        User user = userRepository.findUserByUidAndState(judgmentErrorRequestDto.getUserUid(), State.ACTIVE.getId());
        Judgment judgment = judgmentRepository.findJudgmentByUidAndState(judgmentErrorRequestDto.getJudgmentUid(), State.ACTIVE.getId());
        if (user == null || judgment == null)   return null;

        JudgmentError judgmentError = new JudgmentError(user, judgment, judgmentErrorRequestDto.getError(), judgmentErrorRequestDto.getErrorContent());
        judgmentErrorRepository.save(judgmentError);
        return judgmentError;
    }
}
