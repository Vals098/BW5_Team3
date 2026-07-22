package epic_energy.BW5_Team3.payloads.responseDTOs;

public record ProvinceResponseDTO(
        Long provinceId,
        String abbreviation,
        String provinceName,
        String region
) {}