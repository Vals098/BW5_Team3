package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Client;
import epic_energy.BW5_Team3.entities.Invoice;
import epic_energy.BW5_Team3.entities.InvoiceStatus;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.InvoiceDTO;
import epic_energy.BW5_Team3.repositories.ClientRepository;
import epic_energy.BW5_Team3.repositories.InvoiceRepository;
import epic_energy.BW5_Team3.repositories.InvoiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;


    //CRUD basilari
    public Invoice save(InvoiceDTO body) {
        Client client = clientRepository.findById(body.clientId()).orElseThrow(() -> new NotFoundException("Client with id; " + body.clientId() + " not found"));

        InvoiceStatus status = invoiceStatusRepository.findById(body.invoiceStatusId()).orElseThrow(() -> new NotFoundException("Invoice status not found"));

        Invoice invoice = new Invoice(body.date(), body.amount(), body.number(), client, status);
        return invoiceRepository.save(invoice);
    }

    public Page<Invoice> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Invoice findById(UUID id) {
        return invoiceRepository.findById(id).orElseThrow(() -> new NotFoundException("Invoice with id; " + id + " not found"));
    }

    public Invoice findByIdAndUpdate(UUID id, InvoiceDTO body) {
        Invoice found = this.findById(id);

        Client client = clientRepository.findById(body.clientId()).orElseThrow(() -> new NotFoundException("Client with id; " + body.clientId() + " not found"));

        InvoiceStatus status = invoiceStatusRepository.findById(body.invoiceStatusId())
                .orElseThrow(() -> new NotFoundException("Invoice status not found"));

        found.setDate(body.date());
        found.setAmount(body.amount());
        found.setNumber(body.number());
        found.setClient(client);
        found.setInvoiceStatus(status);

        return invoiceRepository.save(found);
    }

    public void findByIdAndDelete(UUID id){
        Invoice found= this.findById(id);
        invoiceRepository.delete(found);
    }

    //Filtri

    //Filtro per clienti
    public Page<Invoice> findByClient(UUID clientId, Pageable pageable){
        return invoiceRepository.findByClientClientId(clientId, pageable);
    }

    //Filtro per stato
    public Page<Invoice> findByStatus(int statusId, Pageable pageable){
        return invoiceRepository.findByInvoiceStatusInvoiceStatusId(statusId, pageable);
    }

    //Filtro per data
    public Page<Invoice> findByDate(LocalDate date, Pageable pageable){
        return invoiceRepository.findByDate(date, pageable);
    }

    //Filtro per anno
    public Page<Invoice> findByYear(int year, Pageable pageable){
        return invoiceRepository.findByYear(year, pageable);
    }

    //Filtro per range di importi
    public Page<Invoice> findByAmountBetween(double min, double max, Pageable pageable){
        return invoiceRepository.findByAmountBetween(min, max, pageable);
    }

}
