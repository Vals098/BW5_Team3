package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleDTO(
        @NotBlank(message = "The role name is required")
        @Size(min = 2, max = 30, message = "The role name must be between 2 and 30 characters long")
        String role
) {}