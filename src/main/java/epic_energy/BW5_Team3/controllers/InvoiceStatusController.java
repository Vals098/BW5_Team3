package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.InvoiceStatus;
import epic_energy.BW5_Team3.payloads.InvoiceStatusDTO;
import epic_energy.BW5_Team3.services.InvoiceStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices/statuses")
@PreAuthorize("hasAuthority('ADMIN')")
public class InvoiceStatusController {
    @Autowired
    private InvoiceStatusService invoiceStatusService;

    @GetMapping
    public List<InvoiceStatus> getAll() {
        return invoiceStatusService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceStatus createStatus(@RequestBody @Validated InvoiceStatusDTO body) {
        return invoiceStatusService.save(body);
    }
}
