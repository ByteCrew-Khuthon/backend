package kyonggiuniv.bytecrew.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BarnEnvironment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long barnId;

    private Date date = new Date();

    @Column()
    private Double temp;

    private Double humidity;

    public String toString(){
        return "날짜 : "+date+" - 온도: "+temp+", 습도: "+humidity;
    }
}
