package epic_energy.BW5_Team3.services;


import epic_energy.BW5_Team3.entities.Address;
import epic_energy.BW5_Team3.entities.Client;
import epic_energy.BW5_Team3.enums.ClientType;
import epic_energy.BW5_Team3.exceptions.BadRequestException;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.requestDTOs.ClientRequestDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.ClientResponseDTO;
import epic_energy.BW5_Team3.repositories.ClientRepository;
import epic_energy.BW5_Team3.specifications.ClientSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ClientService {

    //    DEFAULT_LOGO
    public static final String DEFAULT_LOGO = "https://www.svgrepo.com/svg/508699/landscape-placeholder";

    //    DIs
    private final ClientRepository clientRepository;
    private final AddressService addressService;

    public ClientService(ClientRepository clientRepository, AddressService addressService) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
    }

    //    CHECK DUPLICATES METHODS
//     IVA
    private void checkDuplicateVat(String iva) {
        if (clientRepository.findByIva(iva).isPresent()) {
            throw new BadRequestException("The iva number already exists.");
        }
    }

    //     email
    private void checkDuplicateEmail(String email) {
        if (clientRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException("The email already exists.");
        }
    }

    //     PEC
    private void checkDuplicatePec(String pec) {
        if (pec != null && clientRepository.findByPec(pec).isPresent()) {
            throw new BadRequestException("The PEC already exists.");
        }
    }

    //    SAVE METHOD
    public ClientResponseDTO save(ClientRequestDTO payload) {
//        1. CONTROLS
//        validation methods
        checkDuplicateVat(payload.iva());
        checkDuplicateEmail(payload.email());
        checkDuplicatePec(payload.pec());

//        String in ClientType, try{value.of(payload.clientType)} catch{BadRequestException("Invalid Client Type")
        ClientType clientType;
        try {
            clientType = ClientType.valueOf(payload.clientType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid client type.");
        }
//        FIRST ADDRESS IDEA
//        address already in DB, get addresses from AddressService.findById()

//        Address legalAddress = addressService.findById(payload.legalAddressId());
//
//        Address operationalAddress = null;
//
//        if (payload.operationalAddressId() != null) {
//            operationalAddress = addressService.findById(payload.operationalAddressId());
//        }

//        SECOND ADDRESS IDEA
        Address legalAddress =
                addressService.save(payload.legalAddress());

        Address operationalAddress = null;

        if (payload.operationalAddress() != null) {
            operationalAddress =
                    addressService.save(payload.operationalAddress());
        }

//        2. CREATE CLIENT
        Client client = new Client();

        client.setLegalName(payload.legalName());
        client.setIva(payload.iva());
        client.setEmail(payload.email());
        client.setPec(payload.pec());

        client.setPhoneNumber(payload.phoneNumber());

        client.setContactName(payload.contactName());
        client.setContactSurname(payload.contactSurname());
        client.setContactEmail(payload.contactEmail());
        client.setContactPhoneNumber(payload.contactPhoneNumber());

        client.setYearlyIncome(payload.yearlyIncome());

        client.setClientType(clientType);

        client.setLegalAddress(legalAddress);
        client.setOperationalAddress(operationalAddress);

        client.setEntryDate(LocalDate.now());
        client.setLogo(DEFAULT_LOGO); //future PATCH to update logo

//        logo idea 2
//        if logo null or isBlank = DEFAULT_LOGO
//        if (payload.logo() == null || payload.logo().isBlank()) {
//            client.setLogo(DEFAULT_LOGO);
//        } else {
//            client.setLogo(payload.logo());
//        }


//        3. SAVE
        Client saved = clientRepository.save(client);

//        4. RETURN
        return new ClientResponseDTO(saved.getClientId());
    }

    //    FIND BY ID
    public Client findById(UUID clientId) {
        return this.clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("The client with id: '" + clientId + "' has not been found."));
    }

    public List<Client> orderByParam(Sort sort) {
        return clientRepository.findAll(sort);
    }

}
