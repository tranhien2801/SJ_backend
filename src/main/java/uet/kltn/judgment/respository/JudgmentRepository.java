package uet.kltn.judgment.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Judgment;

import java.util.List;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, String> {
    Judgment findByUid(String uid);

    List<Judgment> findByJudgmentNumberInAndState(List<String> judgmentNumber, int state);

    Judgment findJudgmentByUidAndState(String uid, int state);

    List<Judgment> findByUidInAndState(List<String> uids, int state);

    Page<Judgment> findAllByState(Pageable pageable, int state);
}
