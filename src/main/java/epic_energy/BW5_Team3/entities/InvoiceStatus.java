package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Changelog;

@Entity
@Table(name = "invoice_statuses")
@Getter
@Setter
@NoArgsConstructor
public class InvoiceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceStatusId;

    @Column(nullable = false, unique = true)
    private String status;

    public InvoiceStatus(String status) {
        this.status = status;
    }
}
