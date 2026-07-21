package epic_energy.BW5_Team3.controllers;


import epic_energy.BW5_Team3.exceptions.ValidationException;
import epic_energy.BW5_Team3.payloads.requestDTOs.ClientRequestDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.ClientResponseDTO;
import epic_energy.BW5_Team3.services.ClientService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //    CRUD
//    POST {base_url}/clients + payload   USER, ADMIN
    @PostMapping
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
//    GET (base_url}/clients/{id}  USER, ADMIN
//    PUT {base_url}/clients/{id} + payload  ADMIN
//    DELETE (base_url}/clients/{id}  ADMIN

}
