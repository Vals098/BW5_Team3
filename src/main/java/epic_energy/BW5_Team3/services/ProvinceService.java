package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Province;
import epic_energy.BW5_Team3.exceptions.BadRequestException;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.requestDTOs.ProvinceDTO;
import epic_energy.BW5_Team3.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    // SAVE / CREATE
    public Province save(ProvinceDTO body) {
        if (provinceRepository.existsByProvinceName(body.provinceName())) {
            throw new BadRequestException("Province with name '" + body.provinceName() + "' already exists.");
        }

        if (provinceRepository.existsByAbbreviation(body.abbreviation())) {
            throw new BadRequestException("Province with abbreviation '" + body.abbreviation() + "' already exists.");
        }

        Province province = new Province();
        province.setAbbreviation(body.abbreviation());
        province.setProvinceName(body.provinceName());
        province.setRegion(body.region());

        return provinceRepository.save(province);
    }

    // FIND ALL (GET ALL endpoint logic)
    public Page<Province> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return provinceRepository.findAll(pageable);
    }

    // FIND BY ID
    public Province findById(Long provinceId) {
        return provinceRepository.findById(provinceId)
                .orElseThrow(() -> new NotFoundException("Province with ID " + provinceId + " not found."));
    }

    // FIND BY NAME (Import CSV and relations)
    public Province findByProvinceName(String provinceName) {
        return provinceRepository.findByProvinceName(provinceName)
                .orElseThrow(() -> new NotFoundException("Province with name '" + provinceName + "' not found."));
    }

    // UPDATE
    public Province findByIdAndUpdate(Long provinceId, ProvinceDTO body) {
        Province found = this.findById(provinceId);

        if (!found.getProvinceName().equalsIgnoreCase(body.provinceName()) && provinceRepository.existsByProvinceName(body.provinceName())) {
            throw new BadRequestException("Province with name '" + body.provinceName() + "' already exists.");
        }

        found.setAbbreviation(body.abbreviation());
        found.setProvinceName(body.provinceName());
        found.setRegion(body.region());

        return provinceRepository.save(found);
    }

    // DELETE
    public void findByIdAndDelete(Long provinceId) {
        Province found = this.findById(provinceId);
        provinceRepository.delete(found);
    }
}