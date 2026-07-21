package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    private UUID addressId;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private int houseNumber;

    @Column(nullable = false)
    private String locality;

    @Column(nullable = false)
    @Pattern(regexp = "^\\d{5}$")
    private String cap;

    @ManyToOne
    @JoinColumn(name = "municipality_id", nullable = false)
    private Comune municipality;

}
