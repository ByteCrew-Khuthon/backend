package kyonggiuniv.bytecrew.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Data
public class DiseaseRisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Double latitude;

    @Column()
    private Double longitude;

    @Column
    private String location;

    @Column()
    private String name;
}
