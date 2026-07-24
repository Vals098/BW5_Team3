package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeUpdatePasswordDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeUpdateProfileDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.UpdateRoleDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.EmployeeResponseDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.MessageResponseDTO;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public MessageResponseDTO updateRole(
            @PathVariable UUID employeeId,
            @RequestBody @Validated UpdateRoleDTO payload) {

        return employeeService.updateRole(employeeId, payload);

    }

    //    ---------------------- /me -------------------------
    // GET /employees/me
    @GetMapping("/me")
    public EmployeeResponseDTO getMyProfile(
            @AuthenticationPrincipal Employee currentEmployee) {
        return employeeService.getMyProfile(currentEmployee);
    }

    //    PUT /employees/me
    @PutMapping("/me")
    public EmployeeResponseDTO updateMyProfile(
            @AuthenticationPrincipal Employee currentEmployee,
            @RequestBody @Valid EmployeeUpdateProfileDTO body) {

        return employeeService.updateMyProfile(currentEmployee, body);
    }

    //    PATCH /employees/me/password
    @PatchMapping("/me/password")
    public MessageResponseDTO updateMyPassword(
            @AuthenticationPrincipal Employee currentEmployee,
            @RequestBody @Valid EmployeeUpdatePasswordDTO body) {

        return employeeService.updateMyPassword(currentEmployee, body);
    }

    //    PATCH /employees/me/avatar
    @PatchMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmployeeResponseDTO updateMyAvatar(
            @AuthenticationPrincipal Employee currentEmployee,
            @RequestParam("avatar") MultipartFile avatar) {

        return employeeService.updateMyAvatar(currentEmployee, avatar);
    }

}
