package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.payloads.EmployeeDTO;
import epic_energy.BW5_Team3.repositories.EmployeeRepository;
import epic_energy.BW5_Team3.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Employee save(EmployeeDTO body) {
        // 1. Obtener las entidades Role a partir de los IDs enviados
        List<Role> rolesList = roleRepository.findAllById(body.getRolesIds());

        if (rolesList.isEmpty()) {
            throw new RuntimeException("Los roles especificados no existen");
        }

        // 2. Mapear DTO a Entity
        Employee newEmployee = new Employee();
        newEmployee.setUsername(body.getUsername());
        newEmployee.setEmail(body.getEmail());
        newEmployee.setPassword(body.getPassword()); // Recuerda aplicar BCryptPasswordEncoder aquí si usas Spring Security
        newEmployee.setName(body.getName());
        newEmployee.setSurname(body.getSurname());

        if (body.getAvatar() != null && !body.getAvatar().isBlank()) {
            newEmployee.setAvatar(body.getAvatar());
        }

        newEmployee.setRoles(new HashSet<>(rolesList));

        // 3. Guardar en BD
        return employeeRepository.save(newEmployee);
    }
}
