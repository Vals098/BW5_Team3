package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "province")
@Getter
@Setter
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long provinceId;

    @Column(nullable = false, unique = true)
    private String abbreviation;

    @Column(nullable = false)
    private String provinceName;

    @Column(nullable = false)
    private String region;



}
