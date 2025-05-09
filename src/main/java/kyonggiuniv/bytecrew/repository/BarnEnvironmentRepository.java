package kyonggiuniv.bytecrew.repository;

import kyonggiuniv.bytecrew.controller.PigManageController;
import kyonggiuniv.bytecrew.entity.BarnEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarnEnvironmentRepository extends JpaRepository<BarnEnvironment, Long> {
}
