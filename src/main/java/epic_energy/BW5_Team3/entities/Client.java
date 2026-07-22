package epic_energy.BW5_Team3.entities;

import epic_energy.BW5_Team3.enums.ClientType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"legalAddress", "operationalAddress"})
public class
Client {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID clientId;

    @Column(nullable = false)
    private String legalName;

    @Column(nullable = false, unique = true)
    private String iva;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate entryDate;

    private LocalDate lastContactDate;

    @Column(precision = 15, scale = 2)
    private BigDecimal yearlyIncome;

    @Column(unique = true)
    private String pec;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(unique = true)
    private String contactEmail;

    @Column(nullable = false)
    private String contactName;

    @Column(nullable = false)
    private String contactSurname;

    @Column(nullable = false)
    private String contactPhoneNumber;

    private String logo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType clientType;

    @ManyToOne
    @JoinColumn(name = "legal_address_id", nullable = false)
    private Address legalAddress;

    @ManyToOne
    @JoinColumn(name = "operational_address_id")
    private Address operationalAddress;

    public Client(ClientType clientType, String contactEmail, String contactName, String contactPhoneNumber, String contactSurname, String email, LocalDate entryDate, String iva, Address legalAddress, String legalName, Address operationalAddress, String pec, String phoneNumber, BigDecimal yearlyIncome, String logo) {
        this.clientType = clientType;
        this.contactEmail = contactEmail;
        this.contactName = contactName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.contactSurname = contactSurname;
        this.email = email;
        this.iva = iva;
        this.legalAddress = legalAddress;
        this.legalName = legalName;
        this.operationalAddress = operationalAddress;
        this.pec = pec;
        this.phoneNumber = phoneNumber;
        this.yearlyIncome = yearlyIncome;
        this.logo = logo; //nel service se null o isBlank setLogo(DEFAULT_LOGO) else setLogo(payload.logo())
        this.entryDate = entryDate; //nel service LocalDate.now()
    }
}

