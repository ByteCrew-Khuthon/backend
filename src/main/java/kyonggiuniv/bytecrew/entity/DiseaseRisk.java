package kyonggiuniv.bytecrew.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
public class DiseaseRisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Double latitude;

    @Column()
    private Double longitude;

    @Column()
    private Double degree;

    @Column()
    private String name;

    @Column
    private String description;

    @Column()
    private Date startDate;

    @Column()
    private Date endDate;
}
