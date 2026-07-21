package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.entities.Role;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.repositories.EmployeeRepository;
import epic_energy.BW5_Team3.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Employee save(EmployeeDTO body) {

        List<Role> rolesList = roleRepository.findAllById(body.getRolesIds());

        if (rolesList.isEmpty()) {
            throw new RuntimeException("Los roles especificados no existen");
        }


        Employee newEmployee = new Employee();
        newEmployee.setUsername(body.getUsername());
        newEmployee.setEmail(body.getEmail());
        newEmployee.setPassword(body.getPassword());
        newEmployee.setName(body.getName());
        newEmployee.setSurname(body.getSurname());

        if (body.getAvatar() != null && !body.getAvatar().isBlank()) {
            newEmployee.setAvatar(body.getAvatar());
        }

        newEmployee.setRoles(new HashSet<>(rolesList));


        return employeeRepository.save(newEmployee);
    }
}
