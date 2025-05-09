package kyonggiuniv.bytecrew.repository;

import kyonggiuniv.bytecrew.entity.DiseaseRisk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRiskRepository extends JpaRepository<DiseaseRisk, Long> {
    List<DiseaseRisk> findByLatitudeAndLongitude(double latitude, double longitude);
}

