package epic_energy.BW5_Team3.payloads.responseDTOs;

import epic_energy.BW5_Team3.entities.Role;

import java.util.Set;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID employeeId,
        String name,
        String surname,
        String email,
        String username,
        String avatar,
        Set<Role> roles
) {
}
