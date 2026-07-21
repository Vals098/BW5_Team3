package epic_energy.BW5_Team3.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationErrorsDTO(String message, List<String> errors, LocalDateTime timestamp) {
}
