package epic_energy.BW5_Team3.repositories;

import epic_energy.BW5_Team3.entities.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Page<Invoice> findByClientClientId(UUID clientId, Pageable pageable);

    Page<Invoice> findByInvoiceStatusInvoiceStatusId(int InvoiceId, Pageable pageable);

    Page<Invoice> findByDate(LocalDate date, Pageable pageable);

    Page<Invoice> findByAmountBetween(double minAmount, double maxAmount, Pageable pageable);

    @Query("SELECT i FROM Invoice i WHERE YEAR(i.date) = :year")
    Page<Invoice> findByYear(@Param("year") int year, Pageable pageable);
}
