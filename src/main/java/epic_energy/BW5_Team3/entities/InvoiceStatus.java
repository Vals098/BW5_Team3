package epic_energy.BW5_Team3.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InvoiceStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceStatusId;

    @Column(name = "Status", nullable = false)
    private String status;

    public InvoiceStatus(String status) {
        this.status = status;
    }
}
