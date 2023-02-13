package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Function;

import java.util.Set;

@Repository
public interface FunctionRepository extends JpaRepository<Function, String> {
    Set<Function> findByFunctionNameInAndState(Set<String> functionNames, int state);
}
