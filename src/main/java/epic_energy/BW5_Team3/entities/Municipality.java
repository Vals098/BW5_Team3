package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipality")
@Getter
@Setter
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codice_provincia_storico")
    private String codiceProvinciaStorico;

    private String progressivoComune;

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

}
