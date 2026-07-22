package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.payloads.requestDTOs.RoleDTO;
import epic_energy.BW5_Team3.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // GET /roles 
    @GetMapping
    public Page<Role> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "roleId") String sortBy
    ) {
        return roleService.findAll(page, size, sortBy);
    }

    // GET /roles/{id}
    @GetMapping("/{roleId}")
    public Role getById(@PathVariable int roleId) {
        return roleService.findById(roleId);
    }

    // POST /roles (Solo ADMIN)
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@RequestBody @Valid RoleDTO body) {
        return roleService.save(body);
    }

    // PUT /roles/{id} (Solo ADMIN)
    @PutMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role updateRole(@PathVariable int roleId, @RequestBody @Valid RoleDTO body) {
        return roleService.findByIdAndUpdate(roleId, body);
    }

    // DELETE /roles/{id} (Solo ADMIN)
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable int roleId) {
        roleService.findByIdAndDelete(roleId);
    }
}