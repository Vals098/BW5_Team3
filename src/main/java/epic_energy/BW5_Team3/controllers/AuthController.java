package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.EmployeeLoginDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.payloads.responseDTOs.EmployeeResponseDTO;
import epic_energy.BW5_Team3.services.AuthService;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthService authService;

    // POST {base_url}/auth/register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDTO register(@RequestBody @Valid EmployeeDTO body) {
            Employee newEmployee = employeeService.save(body);
            return new EmployeeResponseDTO(
                    newEmployee.getEmployeeId(),
                    newEmployee.getUsername(),
                    newEmployee.getEmail(),
                    newEmployee.getName(),
                    newEmployee.getSurname(),
                    newEmployee.getAvatar(),
                    newEmployee.getRoles()
            );

    }

    // POST {base_url}/auth/login
    @PostMapping("/login")
    public String login(@RequestBody @Valid EmployeeLoginDTO body) {
        return authService.authenticateAndGenerateToken(body);
    }
}