package epic_energy.BW5_Team3.payloads.responseDTOs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ClientDetailsReponseDTO(
        UUID clientId,

        String legalName,
        String iva,
        String email,
        String pec,
        String phoneNumber,

        String contactName,
        String contactSurname,
        String contactEmail,
        String contactPhoneNumber,

        BigDecimal yearlyIncome,

        String clientType,

        LocalDate entryDate,

        String logo,

        AddressResponseDTO legalAddress,
        AddressResponseDTO operationalAddress
) {
}
