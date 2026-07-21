package epic_energy.BW5_Team3.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class InvoiceStatus {

    @Id
    @GeneratedValue
    private int invoiceStatusId;
}
