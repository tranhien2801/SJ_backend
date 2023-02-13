package uet.kltn.judgment.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Work;

@Repository
public interface WorkRepository extends JpaRepository<Work, String> {
    Work findByWorkNameAndState(String name, int state);
}
