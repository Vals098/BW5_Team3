package epic_energy.BW5_Team3.services;


import epic_energy.BW5_Team3.entities.Address;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    //    FIND BY ID
    public Address findById(UUID addressId) {
        return addressRepository.findById(addressId).orElseThrow(() -> new NotFoundException("Address not found."));
    }

}
