package epic_energy.BW5_Team3.appConfig;

import epic_energy.BW5_Team3.entities.InvoiceStatus;
import epic_energy.BW5_Team3.entities.Municipality;
import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.enums.ClientType;
import epic_energy.BW5_Team3.payloads.requestDTOs.AddressRequestDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.ClientRequestDTO;
import epic_energy.BW5_Team3.repositories.*;
import epic_energy.BW5_Team3.services.ClientService;
import epic_energy.BW5_Team3.services.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;
    @Autowired
    private ProvinceRepository provinciaRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private InvoiceStatusRepository invoiceStatusRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;

    @Override
    public void run(String... args) throws Exception {
        // importa solo se il database e vuoto
        if (provinciaRepository.count() == 0) {
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

        // Popola i client di test
        seedClients(40);
    }

    // Popola N client di prova, ognuno con un indirizzo legale collegato a un comune reale già importato
    private void seedClients(int count) {
        if (clientRepository.count() > 0) {
            System.out.println("Client già presenti, salto il seeding");
            return;
        }

        List<Municipality> municipalities = municipalityRepository.findAll();
        if (municipalities.isEmpty()) {
            System.out.println("Nessun comune trovato: impossibile creare gli indirizzi dei client di test");
            return;
        }

        Random random = new Random();
        ClientType[] tipi = ClientType.values();
        int creati = 0;

        for (int i = 1; i <= count; i++) {
            Municipality comune = municipalities.get(random.nextInt(municipalities.size()));
            ClientType tipo = tipi[random.nextInt(tipi.length)];

            AddressRequestDTO legalAddress = new AddressRequestDTO(
                    "Via Roma",
                    String.valueOf(random.nextInt(200) + 1),
                    comune.getMunicipalityName(),
                    String.format("%05d", random.nextInt(90000) + 10000),
                    comune.getMunicipalityName()
            );

            String iva = String.valueOf(10000000000L + i); // 11 cifre, univoco per ogni client
            boolean conPec = i % 3 != 0; // 1 client su 3 senza PEC, per varietà

            ClientRequestDTO dto = new ClientRequestDTO(
                    "Azienda Test " + i + " " + tipo,
                    iva,
                    "cliente" + i + "@example.com",
                    conPec ? "cliente" + i + "@pec.it" : null,
                    "+39 333 " + String.format("%07d", i),
                    "Nome" + i,
                    "Cognome" + i,
                    "contatto" + i + "@example.com",
                    "+39 320 " + String.format("%07d", i),
                    BigDecimal.valueOf(10000 + random.nextInt(490000)),
                    tipo.name(),
                    legalAddress,
                    null
            );

            try {
                clientService.save(dto);
                creati++;
            } catch (Exception e) {
                System.out.println("Errore creando il client " + i + ": " + e.getMessage());
            }
        }

        System.out.println("Creati " + creati + " client di test su " + count + " richiesti");
    }
}