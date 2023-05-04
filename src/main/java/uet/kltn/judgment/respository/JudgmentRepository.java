package uet.kltn.judgment.respository;

import org.python.antlr.op.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.User;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface JudgmentRepository extends JpaRepository<Judgment, String> {
    Judgment findByUid(String uid);

    List<Judgment> findByJudgmentNumberInAndState(List<String> judgmentNumber, int state);

    Judgment findJudgmentByUidAndState(String uid, int state);

    List<Judgment> findByUidInAndState(List<String> uids, int state);

    Page<Judgment> findAllByState(Pageable pageable, int state);

    Page<Judgment> findAllByStateOrderByDateIssuedDesc(Pageable pageable, int state);

    List<Judgment> findAllByState(int state);

    @Query("SELECT j FROM Judgment j " +
            "LEFT JOIN j.court jc " +
            "LEFT JOIN j.aCase ja " +
            "WHERE j.judgmentNumber LIKE %:judgmentNumber% " +
            "AND jc.courtLevel LIKE %:courtLevel " +
            "AND j.judgmentLevel LIKE %:judgmentLevel " +
            "AND j.typeDocument LIKE %:typeDocument " +
            "AND ja.caseType LIKE %:caseType " +
            "AND j.state = :state " +
            "AND j.dateIssued >= :dateFrom " +
            "AND j.dateIssued <= :dateTo " +
            "AND j.countVote >= :vote " +
            "ORDER BY j.dateIssued DESC ")
    Page<Judgment> findByFilterAndState(Pageable pageable, String judgmentNumber, String courtLevel, String judgmentLevel, String typeDocument, String caseType, Date dateFrom, Date dateTo, Integer vote, int state);

    @Query("SELECT j FROM Judgment j " +
            "LEFT JOIN j.court jc " +
            "LEFT JOIN j.aCase ja " +
            "WHERE j.judgmentNumber LIKE %:judgmentNumber% " +
            "AND jc.courtLevel LIKE %:courtLevel " +
            "AND j.judgmentLevel LIKE %:judgmentLevel " +
            "AND j.typeDocument LIKE %:typeDocument " +
            "AND ja.caseType LIKE %:caseType " +
            "AND j.state = :state " +
            "AND j.dateIssued >= :dateFrom " +
            "AND j.dateIssued <= :dateTo " +
            "AND j.countVote >= :vote " +
            "AND j.precedent = :precedent " +
            "ORDER BY j.dateIssued DESC ")
    Page<Judgment> findByFilterAndStateAndPrecedent(Pageable pageable, String judgmentNumber, String courtLevel, String judgmentLevel, String typeDocument, String caseType, Date dateFrom, Date dateTo, Integer vote, Integer precedent, int state);

    @Query("SELECT j.judgmentLevel FROM Judgment j GROUP BY j.judgmentLevel")
    Set<String> findJudgmentLevels();

    @Query(value = "SELECT COUNT(*) FROM Judgment j " +
            "LEFT JOIN j.aCase jc GROUP BY jc.caseType")
    Set<?> countByCaseType();

    Integer countAllByState(int state);

    @Query("SELECT MAX(j.dateIssued) FROM Judgment j")
    Date maxDateIssued();

    @Query("SELECT MIN(j.dateIssued) FROM Judgment j")
    Date minDateIssued();


}
