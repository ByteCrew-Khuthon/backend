package kyonggiuniv.bytecrew.repository;

import kyonggiuniv.bytecrew.entity.PigCough;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PigCoughRepository extends JpaRepository<PigCough, Long> {
    Long countByBarnIdAndCreatedAtAfter(Long barnId, Date createdAt);
}
