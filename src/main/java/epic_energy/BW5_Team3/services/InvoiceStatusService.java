package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.InvoiceStatus;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.InvoiceStatusDTO;
import epic_energy.BW5_Team3.repositories.InvoiceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceStatusService {

    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    public List<InvoiceStatus> findAll() {
        return invoiceStatusRepository.findAll();
    }

    public InvoiceStatus findById(Integer id) {
        return invoiceStatusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice status with id: "+ id + " not found"));
    }

    public InvoiceStatus save(InvoiceStatusDTO body) {
        InvoiceStatus status = new InvoiceStatus();
        status.setStatus(body.status());
        return invoiceStatusRepository.save(status);
    }
}
