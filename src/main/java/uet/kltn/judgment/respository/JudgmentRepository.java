package uet.kltn.judgment.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Judgment;

import java.util.List;
import java.util.Set;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, String> {
    Judgment findByUid(String uid);

    List<Judgment> findByJudgmentNumberInAndState(List<String> judgmentNumber, int state);

    Judgment findJudgmentByUidAndState(String uid, int state);

    List<Judgment> findByUidInAndState(List<String> uids, int state);

    Page<Judgment> findAllByState(Pageable pageable, int state);

    List<Judgment> findAllByState(int state);

    @Query("SELECT j FROM Judgment j " +
            "LEFT JOIN j.court jc " +
            "LEFT JOIN j.aCase ja " +
            "WHERE j.judgmentNumber LIKE %:judgmentNumber% " +
            "AND jc.courtLevel LIKE %:courtLevel " +
            "AND j.judgmentLevel LIKE %:judgmentLevel " +
            "AND j.typeDocument LIKE %:typeDocument " +
            "AND ja.caseType LIKE %:caseType " +
            "AND j.state = :state")
    Page<Judgment> findByFilterAndState(Pageable pageable, String judgmentNumber, String courtLevel, String judgmentLevel, String typeDocument, String caseType, int state);

    @Query("SELECT j.judgmentLevel FROM Judgment j GROUP BY j.judgmentLevel")
    Set<String> findJudgmentLevels();


}
