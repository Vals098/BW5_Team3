package epic_energy.BW5_Team3.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Changelog;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID invoiceId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private int number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invoice_status_id")
    private InvoiceStatus invoiceStatus;

    public Invoice(LocalDate date, double amount, int number, Client client, InvoiceStatus invoiceStatus) {
        this.date = date;
        this.amount = amount;
        this.number = number;
        this.client = client;
        this.invoiceStatus = invoiceStatus;
    }
}
