package kyonggiuniv.bytecrew.repository;

import kyonggiuniv.bytecrew.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findTopByCheckingFalseOrderByDateDesc();
    List<Alarm> findAllByOrderByDateDesc();

}

