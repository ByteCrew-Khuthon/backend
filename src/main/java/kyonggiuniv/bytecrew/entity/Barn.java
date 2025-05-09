package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Barn {
    @Id
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
