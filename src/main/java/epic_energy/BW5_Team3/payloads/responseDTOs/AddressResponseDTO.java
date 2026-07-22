package epic_energy.BW5_Team3.payloads.responseDTOs;


import java.util.UUID;

public record AddressResponseDTO(
        UUID addressId,
        String street,
        String houseNumber,
        String locality,
        String cap,
        String municipalityName,
        String provinceName
) {}