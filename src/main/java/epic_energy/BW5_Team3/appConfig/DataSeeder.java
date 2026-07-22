package epic_energy.BW5_Team3.appConfig;


import epic_energy.BW5_Team3.entities.InvoiceStatus;
import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.repositories.InvoiceStatusRepository;
import epic_energy.BW5_Team3.repositories.MunicipalityRepository;
import epic_energy.BW5_Team3.repositories.ProvinceRepository;
import epic_energy.BW5_Team3.repositories.RoleRepository;
import epic_energy.BW5_Team3.services.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;

    @Override
    public void run(String... args) throws Exception {
        // importa solo se il database e vuoto
        if (provinceRepository.count() == 0) {
            int nProvince = csvImportService.importaProvince("province-italiane.csv");
            System.out.println("Importate " + nProvince + " province");
        } else {
            System.out.println("Province già presenti, salto l'import");
        }

        if (municipalityRepository.count() == 0) {
            int nComuni = csvImportService.importaComuni("comuni-italiani.csv");
            System.out.println("Importati " + nComuni + " comuni");
        } else {
            System.out.println("Comuni già presenti, salto l'import");
        }

        // Popola i ruoli
        if (roleRepository.count() == 0) {
            Role admin = new Role("ADMIN");
            Role user = new Role("USER");
            Role superAdmin = new Role("SUPERADMIN");
            List<Role> roles = new ArrayList<>(List.of(admin, user, superAdmin));
            roleRepository.saveAll(roles);
        }

        // Popola gli invoicestatus
        if (invoiceStatusRepository.count() == 0) {
            InvoiceStatus paid = new InvoiceStatus("PAID");
            InvoiceStatus pending = new InvoiceStatus("PENDING");
            InvoiceStatus overdue = new InvoiceStatus("OVERDUE");
            List<InvoiceStatus> invoiceStatuses = new ArrayList<>(List.of(paid, pending, overdue));
            invoiceStatusRepository.saveAll(invoiceStatuses);
        }
    }
}