package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
        @NotBlank(message = "Street is required.")
        String street,

        @NotBlank(message = "House number is required.")
        String houseNumber,

        @NotBlank(message = "The locality is required.")
        String locality,

        @NotBlank(message = "CAP is required.")
        String cap,

        @NotBlank(message = "Municipality is required.")
        String municipalityName
) {
}
