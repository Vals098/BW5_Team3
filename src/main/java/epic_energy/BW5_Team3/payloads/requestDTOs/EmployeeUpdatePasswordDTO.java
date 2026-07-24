package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeUpdatePasswordDTO(

        @NotBlank(message = "Current password is required.")
        String currentPassword,

        @NotBlank(message = "New password is required.")
        @Size(min = 8, message = "Password must contain at least 8 characters.")
        String newPassword

) {
}