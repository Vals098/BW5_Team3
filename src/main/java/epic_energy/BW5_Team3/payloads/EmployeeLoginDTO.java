package epic_energy.BW5_Team3.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeLoginDTO {

    @NotBlank(message = "The email is required")
    @Email(message = "The email format is invalid")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;
}