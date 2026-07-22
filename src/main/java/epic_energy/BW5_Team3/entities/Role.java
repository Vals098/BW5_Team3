package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private int roleId;

    @Column(nullable = false)
    private String role;

    public Role(String role) {
        this.role = role;
    }
}
