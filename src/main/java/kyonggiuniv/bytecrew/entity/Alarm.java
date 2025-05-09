package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column()
    private String title;

    @Column()
    private String description;

    @Column()
    private Date date;

    @Column()
    private boolean checking;
}
