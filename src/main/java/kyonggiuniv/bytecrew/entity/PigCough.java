package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PigCough {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private Long barnId;

    @Column()
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public PigCough(Long barnId) {
        this.barnId = barnId;
    }
}
