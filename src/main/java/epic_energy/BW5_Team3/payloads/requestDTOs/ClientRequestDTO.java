package epic_energy.BW5_Team3.payloads.requestDTOs;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ClientRequestDTO(

        @NotBlank(message = "The legal name is required.")
        @Size(min = 2, max = 150, message = "The legal name must be between 2 and 100 characters.")
        String legalName,

        @NotBlank(message = "The Piva is required.")
        @Pattern(regexp = "^\\d{11}$", message = "The Piva must contain exactly 11 digits.") //valid iva control
        String iva,

        @NotBlank(message = "The email is required.")
        @Email(message = "Invalid email format.")
        String email,

        @Email(message = "Invalid PEC format.")
        String pec,

        @NotBlank(message = "The phone number is required.")
        @Size(min = 5, max = 20)
        @Pattern(regexp = "^[+0-9 ]+$", message = "Invalid phone number.") //valid number control
        String phoneNumber,

        @NotBlank(message = "The contact name is required.")
        @Size(min = 3, max = 50)
        String contactName,

        @NotBlank(message = "The contact surname is required.")
        @Size(min = 3, max = 50)
        String contactSurname,

        @NotBlank(message = "The email is required.")
        @Email(message = "Invalid email format.")
        String contactEmail,

        @NotBlank(message = "The contact Phone Number is required.")
        @Size(min = 5, max = 20)
        @Pattern(regexp = "^[+0-9 ]+$", message = "Invalid phone number.") //valid number control
        String contactPhoneNumber,

        @NotNull(message = "Yearly income is required.")
        @PositiveOrZero(message = "Yearly income cannot be negative.")
        BigDecimal yearlyIncome,

        @NotBlank(message = "The client type is required.")
        String clientType,

        @NotNull(message = "The legal address is required.")
        UUID legalAddressId,

        UUID operationalAddressId
) {
}
