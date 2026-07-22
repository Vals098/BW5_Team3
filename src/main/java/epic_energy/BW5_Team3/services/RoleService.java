package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.exceptions.BadRequestException;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.requestDTOs.RoleDTO;
import epic_energy.BW5_Team3.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // CREATE
    public Role save(RoleDTO body) {
        String formattedRole = body.role().toUpperCase().trim();

        if (roleRepository.existsByRole(formattedRole)) {
            throw new BadRequestException("The role " + formattedRole + " already exists.");
        }

        Role newRole = new Role();
        newRole.setRole(formattedRole);

        return roleRepository.save(newRole);
    }

    // READ ALL
    public Page<Role> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return roleRepository.findAll(pageable);
    }

    // READ BY ID
    public Role findById(int roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role with ID " + roleId + " not found."));
    }

    // UPDATE
    public Role findByIdAndUpdate(int roleId, RoleDTO body) {
        Role found = this.findById(roleId);
        String formattedRole = body.role().toUpperCase().trim();

        if (!found.getRole().equals(formattedRole) && roleRepository.existsByRole(formattedRole)) {
            throw new BadRequestException("The role " + formattedRole + " already exists.");
        }

        found.setRole(formattedRole);
        return roleRepository.save(found);
    }

    // DELETE
    public void findByIdAndDelete(int roleId) {
        Role found = this.findById(roleId);
        roleRepository.delete(found);
    }
}