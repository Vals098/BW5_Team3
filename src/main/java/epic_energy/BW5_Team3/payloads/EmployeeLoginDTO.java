package epic_energy.BW5_Team3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmployeeLoginDTO(
        @NotBlank(message = "The email is required")
        @Email(message = "The email format is invalid")
        String email,

        @NotBlank(message = "The password is required")
        String password
) {}