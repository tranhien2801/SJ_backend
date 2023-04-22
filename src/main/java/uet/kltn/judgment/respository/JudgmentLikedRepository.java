package uet.kltn.judgment.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.History;
import uet.kltn.judgment.model.Judgment;
import uet.kltn.judgment.model.JudgmentLiked;
import uet.kltn.judgment.model.User;

import java.sql.Date;

@Repository
public interface JudgmentLikedRepository extends JpaRepository<JudgmentLiked, String> {
    Boolean existsJudgmentLikedByJudgmentAndUserAndState(Judgment judgment, User user, int state);

    JudgmentLiked findByUserAndJudgmentAndState(User user, Judgment judgment, int state);

    Page<JudgmentLiked> findAllByUserAndStateOrderByCreatedDesc(Pageable pageable, User user, int state);

    @Query("SELECT h FROM JudgmentLiked h " +
            "LEFT JOIN h.judgment.court jc " +
            "LEFT JOIN h.judgment.aCase ja " +
            "WHERE h.user.uid = :userUid " +
            "AND h.judgment.judgmentNumber LIKE %:judgmentNumber% " +
            "AND jc.courtLevel LIKE %:courtLevel " +
            "AND h.judgment.judgmentLevel LIKE %:judgmentLevel " +
            "AND h.judgment.typeDocument LIKE %:typeDocument " +
            "AND ja.caseType LIKE %:caseType " +
            "AND h.judgment.state = :state " +
            "AND h.judgment.dateIssued >= :dateFrom " +
            "AND h.judgment.dateIssued <= :dateTo " +
            "AND h.judgment.countVote >= :vote " +
            "ORDER BY h.judgment.dateIssued DESC ")
    Page<JudgmentLiked> findByFilterAndState(Pageable pageable, String userUid, String judgmentNumber, String courtLevel, String judgmentLevel, String typeDocument, String caseType, Date dateFrom, Date dateTo, Integer vote, int state);

    @Query("SELECT h FROM JudgmentLiked h " +
            "LEFT JOIN h.judgment.court jc " +
            "LEFT JOIN h.judgment.aCase ja " +
            "WHERE h.user.uid = :userUid " +
            "AND h.judgment.judgmentNumber LIKE %:judgmentNumber% " +
            "AND jc.courtLevel LIKE %:courtLevel " +
            "AND h.judgment.judgmentLevel LIKE %:judgmentLevel " +
            "AND h.judgment.typeDocument LIKE %:typeDocument " +
            "AND ja.caseType LIKE %:caseType " +
            "AND h.judgment.state = :state " +
            "AND h.judgment.dateIssued >= :dateFrom " +
            "AND h.judgment.dateIssued <= :dateTo " +
            "AND h.judgment.countVote >= :vote " +
            "AND h.judgment.precedent = :precedent " +
            "ORDER BY h.judgment.dateIssued DESC ")
    Page<JudgmentLiked> findByFilterAndStateAndPrecedent(Pageable pageable, String userUid, String judgmentNumber, String courtLevel, String judgmentLevel, String typeDocument, String caseType, Date dateFrom, Date dateTo, Integer vote, Integer precedent, int state);

}
