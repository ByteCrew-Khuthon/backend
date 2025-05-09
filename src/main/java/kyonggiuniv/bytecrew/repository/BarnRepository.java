package kyonggiuniv.bytecrew.repository;

import kyonggiuniv.bytecrew.entity.Barn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarnRepository extends JpaRepository<Barn, Long> {
    Barn findByLocation(String location);
}
