package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Case;

import java.util.Set;

@Repository
public interface CaseRepository extends JpaRepository <Case, String> {
    @Query(value = "SELECT c.caseType FROM Case c GROUP BY c.caseType")
    Set<String> findCaseTypes ();

}
