package epic_energy.BW5_Team3.services;


import epic_energy.BW5_Team3.entities.Municipality;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.repositories.MunicipalityRepository;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    public MunicipalityService(MunicipalityRepository municipalityRepository) {
        this.municipalityRepository = municipalityRepository;
    }

    public Municipality findByMunicipalityName(String municipalityName) {

        return municipalityRepository.findByMunicipalityName(municipalityName)
                .orElseThrow(() -> new NotFoundException("Municipality '" + municipalityName + "' not found."));
    }
}
