package epic_energy.BW5_Team3.controllers;


import epic_energy.BW5_Team3.entities.Address;
import epic_energy.BW5_Team3.entities.Client;
import epic_energy.BW5_Team3.exceptions.ValidationException;
import epic_energy.BW5_Team3.payloads.requestDTOs.ClientRequestDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.ClientDetailsReponseDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.ClientResponseDTO;
import epic_energy.BW5_Team3.services.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    //  ------------------------------------  DIs  -----------------------------------
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    //    ------------------------------ CRUD ---------------------------------------
//    POST {base_url}/clients + payload   USER, ADMIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ClientResponseDTO saveClient(@RequestBody @Validated ClientRequestDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errors = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errors);
        }

        return clientService.save(payload);
    }

    //    GET (base_url}/clients   USER, ADMIN
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Page<Client> getAllClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "clientId") String sortBy) {
        return clientService.findAll(page, size, sortBy);
    }

    //    GET (base_url}/clients/{id}  USER, ADMIN
    @GetMapping("/{clientId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Client findById(@PathVariable UUID clientId) {
        return clientService.findById(clientId);
    }

    //    PUT {base_url}/clients/{id} + payload  ADMIN
    @PutMapping("/{clientId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ClientDetailsReponseDTO updateClient(
            @PathVariable UUID clientId,
            @RequestBody @Validated ClientRequestDTO payload,
            BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            throw new ValidationException(
                    validationResult.getFieldErrors()
                            .stream()
                            .map(fieldError -> fieldError.getDefaultMessage())
                            .toList()
            );
        }

        return clientService.updateClient(clientId, payload);
    }

//   ----------- first delete idea -----------
//    DELETE (base_url}/clients/{id}  ADMIN
//   ----------- second delete idea -----------
//    not a true delete, a method that changes boolean isClientActive from true to false
//    and sets the client to read-only

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    //    http://localhost:8080/clients?orderBy=param
    //----------------EXAMPLE------------------------
    //    http://localhost:8080/clients?orderBy=entryDate
    public List<Client> orderByParam(@RequestParam(defaultValue = "legalName") String orderBy) {
        Sort sort = switch (orderBy) {
            case "yearlyIncome" -> Sort.by(Sort.Direction.DESC, "yearlyIncome");
            case "entryDate" -> Sort.by(Sort.Direction.DESC, "entryDate");
            case "lastContactDate" -> Sort.by(Sort.Direction.DESC, "lastContactDate");
            case "province" -> Sort.by(Sort.Direction.ASC, "legalAddress.municipality.province.provinceName");
            default -> Sort.by(Sort.Direction.ASC, "legalName");
        };
        return clientService.orderByParam(sort);
    }


}
