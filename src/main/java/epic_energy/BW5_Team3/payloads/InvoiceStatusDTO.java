package epic_energy.BW5_Team3.payloads;

import jakarta.validation.constraints.NotBlank;

public record InvoiceStatusDTO(
        @NotBlank(message = "Invoice status is required")
        String status
) {}
