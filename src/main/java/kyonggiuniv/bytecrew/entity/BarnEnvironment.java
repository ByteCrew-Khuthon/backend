package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@Getter
public class BarnEnvironment {
    @Id
    private Long id;

    private Long barnId;

    @Column()
    private Double temp;

    private Double humidity;

    public BarnEnvironment() {

    }
}
