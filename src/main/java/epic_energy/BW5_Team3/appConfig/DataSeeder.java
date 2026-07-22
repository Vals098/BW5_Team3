package epic_energy.BW5_Team3.appConfig;

import epic_energy.BW5_Team3.repositories.MunicipalityRepository;
import epic_energy.BW5_Team3.repositories.ProvinceRepository;
import epic_energy.BW5_Team3.repositories.ProvinceRepository;
import epic_energy.BW5_Team3.services.CsvImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;
    @Autowired
    private ProvinceRepository provinciaRepository;
    @Autowired
    private MunicipalityRepository municipalityRepository;

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
    }
}