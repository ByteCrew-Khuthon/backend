package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Barn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String location;

    @Column()
    private Double latitude;

    @Column()
    private Double longitude;

    private Double wantedTemperature;
    private Double wantedHumidity;
}
