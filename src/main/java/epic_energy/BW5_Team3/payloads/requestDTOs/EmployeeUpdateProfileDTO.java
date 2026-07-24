package epic_energy.BW5_Team3.payloads.requestDTOs;

public record EmployeeUpdateProfileDTO(
        String username,
        String email,
        String name,
        String surname
) {
}
