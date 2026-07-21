package epic_energy.BW5_Team3.controllers;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.EmployeeLoginDTO;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
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
    public Employee register(@RequestBody @Valid EmployeeDTO body) {
        return employeeService.save(body);
    }

    // POST {base_url}/auth/login
    @PostMapping("/login")
    public String login(@RequestBody @Valid EmployeeLoginDTO body) {
        return authService.authenticateAndGenerateToken(body);
    }
}