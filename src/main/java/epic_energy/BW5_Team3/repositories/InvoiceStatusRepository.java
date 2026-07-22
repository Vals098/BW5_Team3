package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceStatusRepository extends JpaRepository<InvoiceStatus, Integer> {
    Optional<InvoiceStatus> findByStatus(String status);
}
