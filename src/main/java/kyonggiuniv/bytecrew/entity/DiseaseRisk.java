package kyonggiuniv.bytecrew.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class DiseaseRisk {
    @Id
    private Long id;

    @Column()
    private Double latitude;

    @Column()
    private Double longitude;

    @Column()
    private Double degree;

    @Column()
    private Date startDate;

    @Column()
    private Date endDate;
}
