package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Client;
import epic_energy.BW5_Team3.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//esempio di repo solo per postare i packages, da sviluppare

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Repository
    interface RoleRepository extends JpaRepository<Role, Integer> {


        Optional<Role> findByRole(String role);
    }
}
