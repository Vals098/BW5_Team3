package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipalities")
@Getter
@Setter
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long municipalityId;

    @Column(name = "historical_province_code")
    private String historicalProvinceCode;

    @Column(name = "municipality_code")
    private String municipalityCode;

    @Column(nullable = false)
    private String municipalityName;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

}