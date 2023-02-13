package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.CaseType;

import java.util.Set;

@Repository
public interface CaseTypeRepository extends JpaRepository<CaseType, String> {
    Set<CaseType> findByCaseTypeNameInAndState(Set<String> names, int state);

    Integer countAllByState(int state);

    Set<CaseType> findAllByState(int state);
}
