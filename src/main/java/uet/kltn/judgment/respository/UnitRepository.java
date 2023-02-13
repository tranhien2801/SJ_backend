package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Unit;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit, String> {
    List<Unit> findAllByState(int state);

    Unit findByUnitNameAndState(String name, int state);
}
