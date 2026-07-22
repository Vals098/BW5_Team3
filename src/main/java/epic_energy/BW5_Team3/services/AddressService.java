package epic_energy.BW5_Team3.services;


import epic_energy.BW5_Team3.entities.Address;
import epic_energy.BW5_Team3.entities.Municipality;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.requestDTOs.AddressRequestDTO;
import epic_energy.BW5_Team3.repositories.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final MunicipalityService municipalityService;

    public AddressService(AddressRepository addressRepository, MunicipalityService municipalityService) {
        this.addressRepository = addressRepository;
        this.municipalityService = municipalityService;
    }

    //    FIND BY ID
    public Address findById(UUID addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found."));
    }

    //    SAVE
//    AddressRequestDTO -> find Municipality -> create Address -> save -> return
    public Address save(AddressRequestDTO payload) {

        Municipality municipality = municipalityService.findByMunicipalityName(payload.municipalityName());

        Address address = new Address();

        address.setStreet(payload.street());
        address.setHouseNumber(payload.houseNumber());
        address.setLocality(payload.locality());
        address.setCap(payload.cap());
        address.setMunicipality(municipality);

        return addressRepository.save(address);
    }

    // READ ALL (Paginado)
    public Page<Address> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return addressRepository.findAll(pageable);
    }
    // UPDATE
    public Address findByIdAndUpdate(UUID addressId, AddressRequestDTO payload) {
        Address found = this.findById(addressId);
        Municipality municipality = municipalityService.findByMunicipalityName(payload.municipalityName());

        found.setStreet(payload.street());
        found.setHouseNumber(payload.houseNumber());
        found.setLocality(payload.locality());
        found.setCap(payload.cap());
        found.setMunicipality(municipality);

        return addressRepository.save(found);
    }

    // DELETE
    public void findByIdAndDelete(UUID addressId) {
        Address found = this.findById(addressId);
        addressRepository.delete(found);
    }

}
