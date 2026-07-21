package epic_energy.BW5_Team3.payloads.requestDTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class EmployeeDTO {

    @NotBlank(message = "The username is required")
    @Size(min = 3, max = 20, message = "The username must be between 3 and 20 characters long.")
    private String username;

    @NotBlank(message = "The email is required")
    @Email(message = "The email format is invalid")
    private String email;

    @NotBlank(message = "The password is required")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The surname is required")
    private String surname;

    private String avatar; //Optional (if not provided, the entity assigns the default image)

    @NotEmpty(message = "The employee must have at least one assigned role")
    private Set<Integer> rolesIds;
}
