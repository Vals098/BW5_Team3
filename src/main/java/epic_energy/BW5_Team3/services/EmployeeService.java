package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.exceptions.BadRequestException;
import epic_energy.BW5_Team3.exceptions.NotFoundException;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeUpdatePasswordDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeUpdateProfileDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.UpdateRoleDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.EmployeeResponseDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.MessageResponseDTO;
import epic_energy.BW5_Team3.repositories.EmployeeRepository;
import epic_energy.BW5_Team3.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CREATE / REGISTER
    public Employee save(EmployeeDTO body) {
        if (employeeRepository.existsByEmail(body.email())) {
            throw new BadRequestException("El email " + body.email() + " ya está registrado.");
        }

        if (employeeRepository.existsByUsername(body.username())) {
            throw new BadRequestException("El nombre de usuario " + body.username() + " ya está en uso.");
        }

//        List<Role> rolesList = roleRepository.findAllById(body.rolesIds());
//        if (rolesList.isEmpty()) {
//            throw new BadRequestException("Los roles especificados no existen.");
//        }


        Employee newEmployee = new Employee();
        newEmployee.setUsername(body.username());
        newEmployee.setEmail(body.email());
        newEmployee.setPassword(passwordEncoder.encode(body.password())); // Contraseña encriptada
        newEmployee.setName(body.name());
        newEmployee.setSurname(body.surname());

        if (body.avatar() != null && !body.avatar().isBlank()) {
            newEmployee.setAvatar(body.avatar());
        }

//        newEmployee.setRoles(new HashSet<>(rolesList));
        Role userRole = roleRepository.findByRole("USER").orElseThrow(() -> new NotFoundException("Role USER not found."));
        newEmployee.setRoles(Set.of(userRole));

        return employeeRepository.save(newEmployee);
    }

    // READ ALL
    public Page<Employee> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return employeeRepository.findAll(pageable);
    }

    // READ BY ID
    public Employee findById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException("Empleado con ID " + employeeId + " no encontrado."));
    }

    // UPDATE
    public Employee findByIdAndUpdate(UUID employeeId, EmployeeDTO body) {
        Employee found = this.findById(employeeId);

        if (!found.getEmail().equals(body.email()) && employeeRepository.existsByEmail(body.email())) {
            throw new BadRequestException("El email " + body.email() + " ya está en uso por otro usuario.");
        }

        if (!found.getUsername().equals(body.username()) && employeeRepository.existsByUsername(body.username())) {
            throw new BadRequestException("El username " + body.username() + " ya está en uso por otro usuario.");
        }

//        List<Role> rolesList = roleRepository.findAllById(body.rolesIds());

        found.setUsername(body.username());
        found.setEmail(body.email());
        found.setPassword(passwordEncoder.encode(body.password()));
        found.setName(body.name());
        found.setSurname(body.surname());
        if (body.avatar() != null && !body.avatar().isBlank()) {
            found.setAvatar(body.avatar());
        }
//        found.setRoles(new HashSet<>(rolesList));

        return employeeRepository.save(found);
    }

    // DELETE
    public void findByIdAndDelete(UUID employeeId) {
        Employee found = this.findById(employeeId);
        employeeRepository.delete(found);
    }

    //    UPDATE ROLE
    public MessageResponseDTO updateRole(UUID employeeId, UpdateRoleDTO payload) {

        Employee employee = findById(employeeId);

        Role role = roleRepository.findByRole(payload.role().toUpperCase()).orElseThrow(() -> new NotFoundException("The role " + payload.role() + " doesn't exist."));

        employee.setRoles(new HashSet<>(List.of(role)));

        employeeRepository.save(employee);

        return new MessageResponseDTO("The role of the employee " + employee.getName() + " " + employee.getSurname() + " has been updated to: " + role.getRole());

    }

    //    ------------------------ ME -------------------------------
    //    GET MY PROFILE
    public EmployeeResponseDTO getMyProfile(Employee currentEmployee) {
        return new EmployeeResponseDTO(
                currentEmployee.getEmployeeId(),
                currentEmployee.getName(),
                currentEmployee.getSurname(),
                currentEmployee.getEmail(),
                currentEmployee.getUsername(),
                currentEmployee.getAvatar(),
                currentEmployee.getRoles()
        );
    }

    //    UPDATE MY PROFILE
//    all but password and avatar
    public EmployeeResponseDTO updateMyProfile(Employee currentEmployee, EmployeeUpdateProfileDTO body) {

        if (!currentEmployee.getEmail().equals(body.email())
                && employeeRepository.existsByEmail(body.email())) {
            throw new BadRequestException("Email already in use.");
        }

        if (!currentEmployee.getUsername().equals(body.username())
                && employeeRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Username already in use.");
        }

        currentEmployee.setUsername(body.username());
        currentEmployee.setEmail(body.email());
        currentEmployee.setName(body.name());
        currentEmployee.setSurname(body.surname());

        Employee saved = employeeRepository.save(currentEmployee);

        return new EmployeeResponseDTO(
                saved.getEmployeeId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getName(),
                saved.getSurname(),
                saved.getAvatar(),
                saved.getRoles()
        );
    }

    //    UPDATE PASSWORD
    public MessageResponseDTO updateMyPassword(
            Employee currentEmployee,
            EmployeeUpdatePasswordDTO body) {

        if (!passwordEncoder.matches(body.currentPassword(), currentEmployee.getPassword())) {
            throw new BadRequestException("Current password is incorrect.");
        }

        currentEmployee.setPassword(
                passwordEncoder.encode(body.newPassword())
        );

        Employee saved = employeeRepository.save(currentEmployee);

        return new MessageResponseDTO("Password updated successfully.");
    }

}
