package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Province;
import epic_energy.BW5_Team3.payloads.requestDTOs.ProvinceDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.ProvinceResponseDTO;
import epic_energy.BW5_Team3.services.ProvinceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    private ProvinceResponseDTO mapToResponseDTO(Province province) {
        return new ProvinceResponseDTO(
                province.getProvinceId(),
                province.getAbbreviation(),
                province.getProvinceName(),
                province.getRegion()
        );
    }

    // GET ALL ENDPOINT (/provinces)
    @GetMapping
    public Page<ProvinceResponseDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "provinceId") String sortBy
    ) {
        return provinceService.findAll(page, size, sortBy).map(this::mapToResponseDTO);
    }

    // GET BY ID (/provinces/{id})
    @GetMapping("/{provinceId}")
    public ProvinceResponseDTO getById(@PathVariable Long provinceId) {
        return mapToResponseDTO(provinceService.findById(provinceId));
    }

    // POST /provinces (Solo SUPER_ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProvinceResponseDTO createProvince(@RequestBody @Valid ProvinceDTO body) {
        Province saved = provinceService.save(body);
        return mapToResponseDTO(saved);
    }

    // PUT /provinces/{id} (Solo SUPER_ADMIN)
    @PutMapping("/{provinceId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ProvinceResponseDTO updateProvince(@PathVariable Long provinceId, @RequestBody @Valid ProvinceDTO body) {
        Province updated = provinceService.findByIdAndUpdate(provinceId, body);
        return mapToResponseDTO(updated);
    }

    // DELETE /provinces/{id} (Solo SUPER_ADMIN)
    @DeleteMapping("/{provinceId}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProvince(@PathVariable Long provinceId) {
        provinceService.findByIdAndDelete(provinceId);
    }
}