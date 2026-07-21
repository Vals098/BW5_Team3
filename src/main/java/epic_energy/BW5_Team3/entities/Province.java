package epic_energy.BW5_Team3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Province {

    @Id
    @GeneratedValue
    private UUID provinceId;
}
