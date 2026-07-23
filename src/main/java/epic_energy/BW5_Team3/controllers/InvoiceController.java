package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Invoice;
import epic_energy.BW5_Team3.exceptions.ValidationException;
import epic_energy.BW5_Team3.payloads.InvoiceDTO;
import epic_energy.BW5_Team3.services.InvoiceService;
import epic_energy.BW5_Team3.specifications.InvoiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
//

    /// /        GET {base_url}/client/invoices?clientId=...
//    @GetMapping
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
//    public Page<Invoice> getAll(
//            @RequestParam(required = false) UUID clientId,
//            Pageable pageable) {
//
//        if (clientId != null) {
//            return invoiceService.findByClient(clientId, pageable);
//        }
//        return invoiceService.findAll(pageable);
//    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Page<Invoice> getAllFiltered(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) Integer invoiceStatusId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            Pageable pageable
    ) {
        Specification<Invoice> spec = Specification.where(InvoiceSpecification.hasClient(clientId)
                .and(InvoiceSpecification.hasStatus(invoiceStatusId))
                .and(InvoiceSpecification.hasDate(date))
                .and(InvoiceSpecification.hasYear(year))
                .and(InvoiceSpecification.hasAmountBetween(min, max)));
        return invoiceService.findAll(pageable, spec);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Invoice createInvoice(@RequestBody @Validated InvoiceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> errorsList = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }

        return invoiceService.save(body);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Invoice getById(@PathVariable UUID id) {
        return invoiceService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Invoice updateInvoice(
            @PathVariable UUID id,
            @RequestBody @Validated InvoiceDTO body,
            BindingResult validation) {

        if (validation.hasErrors()) {
            List<String> errorsList = validation.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ValidationException(errorsList);
        }

        return invoiceService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)//204
    public void delete(@PathVariable UUID id) {
        invoiceService.findByIdAndDelete(id);
    }
}
