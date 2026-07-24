package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateProfileDTO(
        @NotBlank(message = "The username is required")
        @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters long.")
        String username,

        @NotBlank(message = "The email is required")
        @Email(message = "The email format is invalid")
        String email,

        @NotBlank(message = "The name is required")
        String name,

        @NotBlank(message = "The surname is required")
        String surname
) {
}
