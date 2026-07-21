package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.exceptions.UnauthorizedException;
import epic_energy.BW5_Team3.payloads.EmployeeLoginDTO;
import epic_energy.BW5_Team3.repositories.EmployeeRepository;
import epic_energy.BW5_Team3.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;


    public String authenticateAndGenerateToken(EmployeeLoginDTO body) {
        Employee employee = employeeRepository.findByEmail(body.email())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        if (!passwordEncoder.matches(body.password(), employee.getPassword())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        return jwtTools.generateToken(employee);
    }
}