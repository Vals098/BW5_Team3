package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.UpdateRoleDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.UpdateRoleResponseDTO;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // GET /employees (ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Page<Employee> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "employeeId") String sortBy
    ) {
        return employeeService.findAll(page, size, sortBy);
    }

    // GET /employees/me
    @GetMapping("/me")
    public Employee getMyProfile(@AuthenticationPrincipal Employee currentEmployee) {
        return currentEmployee;
    }

    // GET /employees/{id}
    @GetMapping("/{employeeId}")
    public Employee getById(@PathVariable UUID employeeId) {
        return employeeService.findById(employeeId);
    }


    // PUT /employees/{id} (Solo ADMIN)
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Employee updateEmployee(@PathVariable UUID employeeId, @RequestBody @Valid EmployeeDTO body) {
        return employeeService.findByIdAndUpdate(employeeId, body);
    }

    // DELETE /employees/{id} (Solo ADMIN)
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable UUID employeeId) {
        employeeService.findByIdAndDelete(employeeId);
    }

    //   PATCH /{employeeId}/role
    @PatchMapping("{employeeId}/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public UpdateRoleResponseDTO updateRole(
            @PathVariable UUID employeeId,
            @RequestBody @Validated UpdateRoleDTO payload) {

        return employeeService.updateRole(employeeId, payload);

    }


}
