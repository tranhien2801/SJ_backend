package uet.kltn.judgment.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.kltn.judgment.model.Unit;
import uet.kltn.judgment.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findByUidIn(List<String> uidList);

    List<User> findByEmailInAndState(List<String> emailList, int state);

    List<User> findByUidInAndState(List<String> uidList, int state);

    @Query("SELECT u from User u  WHERE u.uid = :id")
    User findByUid(String id);

    User findUserByEmailAndState(String email, int state);

    Boolean existsUserByEmail(String email);

    Boolean existsUserByEmailAndUidNot(String email, String uid);

    User findUserByUidAndState(String uid, int state);

    List<User> findUserByUidInAndPowerGreaterThanEqual(List<String> ids, int power);

    Page<User> findByUidAndState(Pageable pageable, String uid, int state);

    Page<User> findAllByStateAndPowerIn(Pageable pageable, int state, List<Integer> listPower);

    Page<User> findAllByPowerIn(Pageable pageable, List<Integer> listPower, String uid);

    Page<User> findAllByPowerInAndUidNot(Pageable pageable, List<Integer> listPower, String uid);

    Page<User> findAllByUnitAndUidNot(Pageable pageable, Unit unit, String uid);


    Page<User> findByLevelAndRoleAndState(Pageable pageable, int level, int role, int state);

    Page<User> findByLevelAndRole(Pageable pageable, int level, int role);

    User findUserByUidAndUsageTimeAndState(String uid, int usageTime, int state);

    Integer countAllBy();

    Integer countAllByLevelAndRole(int level, int role);

}
