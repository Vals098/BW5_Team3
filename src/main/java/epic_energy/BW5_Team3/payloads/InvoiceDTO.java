package epic_energy.BW5_Team3.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record InvoiceDTO (
        @NotNull(message = "The date is required")
        LocalDate date,

        @NotNull(message = "Amount is required")
        @Positive(message = "You can only input positive a amount")
        Double amount,

        @NotNull(message = "Invoice number is required")
        Integer number,

        @NotNull(message = "Client ID is required")
        UUID clientId,

        @NotNull(message = "Invoice status ID is required")
        Integer invoiceStatusId
){}


