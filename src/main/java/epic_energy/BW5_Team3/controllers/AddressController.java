package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Address;
import epic_energy.BW5_Team3.payloads.requestDTOs.AddressRequestDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.AddressResponseDTO;
import epic_energy.BW5_Team3.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Helper to map Entity -> AddressResponseDTO
    private AddressResponseDTO mapToResponseDTO(Address address) {
        return new AddressResponseDTO(
                address.getAddressId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getLocality(),
                address.getCap(),
                address.getMunicipality().getMunicipalityName(),
                address.getMunicipality().getProvince().getProvinceName()
        );
    }

    // GET /addresses
    @GetMapping
    public Page<AddressResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "addressId") String sortBy
    ) {
        return addressService.findAll(page, size, sortBy).map(this::mapToResponseDTO);
    }

    // GET /addresses/{id}
    @GetMapping("/{addressId}")
    public AddressResponseDTO getById(@PathVariable UUID addressId) {
        return mapToResponseDTO(addressService.findById(addressId));
    }

    // POST /addresses
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseDTO createAddress(@RequestBody @Valid AddressRequestDTO body) {
        Address saved = addressService.save(body);
        return mapToResponseDTO(saved);
    }

    // PUT /addresses/{id} (Solo SUPER_ADMIN)
    @PutMapping("/{addressId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public AddressResponseDTO updateAddress(@PathVariable UUID addressId, @RequestBody @Valid AddressRequestDTO body) {
        Address updated = addressService.findByIdAndUpdate(addressId, body);
        return mapToResponseDTO(updated);
    }

    // DELETE /addresses/{id} (Solo SUPER_ADMIN)
    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable UUID addressId) {
        addressService.findByIdAndDelete(addressId);
    }
}