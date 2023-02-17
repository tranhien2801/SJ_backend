package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Court;

import java.util.List;
import java.util.Set;

@Repository
public interface CourtRepository extends JpaRepository<Court, String> {
    List<Court> findAllByState(int state);

    @Query(value = "SELECT c.courtLevel FROM Court c GROUP BY c.courtLevel")
    Set<String> findCourtLevels();

}
