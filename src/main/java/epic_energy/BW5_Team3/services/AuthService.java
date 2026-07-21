package epic_energy.BW5_Team3.services;

import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.EmployeeLoginDTO;
import epic_energy.BW5_Team3.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public String authenticateAndGenerateToken(EmployeeLoginDTO body) {
        Employee employee = employeeRepository.findByEmail(body.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));


        if (!employee.getPassword().equals(body.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }



        return "TOKEN_JWT";
    }
}