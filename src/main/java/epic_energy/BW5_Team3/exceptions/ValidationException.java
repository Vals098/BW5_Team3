package epic_energy.BW5_Team3.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {
    private List<String> errorsList;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<String> errorsList) {
        super("Validation Errors");
        this.errorsList = errorsList;
    }

}
