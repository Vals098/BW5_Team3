package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.payloads.EmailRequestDTO;
import epic_energy.BW5_Team3.services.MailgunService;
import jakarta.validation.Valid;
import kong.unirest.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private MailgunService mailgunService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/send")
    public ResponseEntity<String> sendCustomEmail(@RequestBody @Valid EmailRequestDTO body) {
        JsonNode response = mailgunService.sendEmail(body.to(), body.subject(), body.body());
        return ResponseEntity.ok("Email sent succesfully, message from Mailgun: " + response.toString());
    }
}