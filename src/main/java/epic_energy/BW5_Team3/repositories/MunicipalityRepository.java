package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    Optional<Municipality> findByMunicipalityName(String nome);

}
