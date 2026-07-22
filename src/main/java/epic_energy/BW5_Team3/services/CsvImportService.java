package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Municipality;
import epic_energy.BW5_Team3.entities.Province;
import epic_energy.BW5_Team3.repositories.MunicipalityRepository;
import epic_energy.BW5_Team3.repositories.ProvinceRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvImportService {

    private static final Map<String, String> ALIAS_PROVINCE = Map.ofEntries(
            Map.entry("Ascoli Piceno", "Ascoli-Piceno"),
            Map.entry("Bolzano/Bozen", "Bolzano"),
            Map.entry("Forlì-Cesena", "Forli-Cesena"),
            Map.entry("La Spezia", "La-Spezia"),
            Map.entry("Monza e della Brianza", "Monza-Brianza"),
            Map.entry("Pesaro e Urbino", "Pesaro-Urbino"),
            Map.entry("Reggio Calabria", "Reggio-Calabria"),
            Map.entry("Reggio nell'Emilia", "Reggio-Emilia"),
            Map.entry("Valle d'Aosta/Vallée d'Aoste", "Aosta"),
            Map.entry("Verbano-Cusio-Ossola", "Verbania"),
            Map.entry("Vibo Valentia", "Vibo-Valentia")
    );
    @Autowired
    private ProvinceRepository provinciaRepository;
    @Autowired
    private MunicipalityRepository comuneRepository;

    private String leggiContenutoSenzaBom(String classpathFile) throws IOException {
        InputStream is = new ClassPathResource(classpathFile).getInputStream();
        String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        if (content.startsWith("\uFEFF")) {
            content = content.substring(1); // rimuove il BOM
        }
        return content;
    }

    @Transactional
    public int importaProvince(String csvPath) throws Exception {
        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT).setDelimiter(";").setHeader().setSkipHeaderRecord(true).build();

        List<Province> province = new ArrayList<>();


        try (CSVParser parser = CSVParser.parse(leggiContenutoSenzaBom(csvPath), format)) {
            for (CSVRecord record : parser) {
                Province p = new Province();
                p.setAbbreviation(record.get("Sigla").trim());
                p.setProvinceName(record.get("Provincia").trim());
                p.setRegion(record.get("Regione").trim());
                province.add(p);
            }
        }
        provinciaRepository.saveAll(province);
        return province.size();
    }

    @Transactional
    public int importaComuni(String csvPath) throws Exception {
        // mappa nome , provincia -> entità, per collegare i comuni senza fare una query per riga
        Map<String, Province> mappaProvince = new HashMap<>();
        provinciaRepository.findAll().forEach(p -> mappaProvince.put(p.getProvinceName(), p));
        if (!mappaProvince.containsKey("Sud Sardegna")) {
            Province sudSardegna = new Province();
            sudSardegna.setAbbreviation("SU");
            sudSardegna.setProvinceName("Sud Sardegna");
            sudSardegna.setRegion("Sardegna");
            sudSardegna = provinciaRepository.save(sudSardegna);
            mappaProvince.put("Sud Sardegna", sudSardegna);
        }
        CSVFormat format = CSVFormat.Builder.create(CSVFormat.DEFAULT).setDelimiter(';').setSkipHeaderRecord(true).build();
        List<Municipality> comuni = new ArrayList<>();
        int nonTrovati = 0;
        try (CSVParser parser = CSVParser.parse(leggiContenutoSenzaBom(csvPath), format)) {
            for (CSVRecord record : parser) {
                if (record.size() < 4) continue;
                String nomeProvincia = record.get(3).trim();
                Province province = mappaProvince.get(nomeProvincia);
                if (province == null) {
                    String alias = ALIAS_PROVINCE.get(nomeProvincia);
                    if (alias != null) {
                        province = mappaProvince.get(alias);
                    }
                }
                if (province == null) {
                    nonTrovati++;
                    System.out.println("Provincia non trovata: " + nomeProvincia);
                    continue;
                }
                Municipality c = new Municipality();
                c.setHistoricalProvinceCode(record.get(0).trim());
                c.setMunicipalityCode(record.get(1).trim());
                c.setMunicipalityName(record.get(2).trim());
                c.setProvince(province);
                comuni.add(c);
            }
        }
        comuneRepository.saveAll(comuni);
        System.out.println("Comuni non collegati (provincia non trovata): " + nonTrovati);
        return comuni.size();
    }
}
