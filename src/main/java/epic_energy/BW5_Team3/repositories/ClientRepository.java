package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Client;
import epic_energy.BW5_Team3.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByIva(String iva);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByPec(String pec);

}
