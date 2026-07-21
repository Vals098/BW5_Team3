package epic_energy.BW5_Team3.controllers;


import epic_energy.BW5_Team3.entities.Employee;
import epic_energy.BW5_Team3.payloads.requestDTOs.EmployeeDTO;
import epic_energy.BW5_Team3.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody @Valid EmployeeDTO body) {
        return employeeService.save(body);
    }
}
