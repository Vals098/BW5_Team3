package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    // Métodos útiles para búsquedas comunes y validaciones
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
