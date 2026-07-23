package epic_energy.BW5_Team3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailRequestDTO(
        @NotBlank(message = "Receiver is required")
        @Email(message = "Use a valid email")
        String to,

        @NotBlank(message = "Subject is required")
        String subject,

        @NotBlank(message = "Body of the email is required")
        String body
) {}