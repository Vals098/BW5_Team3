package epic_energy.BW5_Team3.payloads.responseDTOs;

import epic_energy.BW5_Team3.entities.Role;

import java.util.Set;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID employeeId,
        String username,
        String email,
        String name,
        String surname,
        String avatar,
        Set<Role> roles
) {}
