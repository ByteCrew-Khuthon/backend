package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BarnEnvironment {
    @Id
    private Long id;

    private Long barnId;

    @Column()
    private Double temp;

    private Double humidity;
}
