package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.JudgmentError;

@Repository
public interface JudgmentErrorRepository extends JpaRepository<JudgmentError, String> {
    JudgmentError findByUid(String uid);



}
