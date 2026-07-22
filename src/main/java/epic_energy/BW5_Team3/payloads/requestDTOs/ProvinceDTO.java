package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProvinceDTO(
        @NotBlank(message = "Abbreviation is required.")
        @Size(min = 2, max = 5, message = "Abbreviation must be between 2 and 5 characters.")
        String abbreviation,

        @NotBlank(message = "Province name is required.")
        String provinceName,

        @NotBlank(message = "Region is required.")
        String region
) {}