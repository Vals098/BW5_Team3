package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Optional<Provincia> findByNome(String nome);
}
