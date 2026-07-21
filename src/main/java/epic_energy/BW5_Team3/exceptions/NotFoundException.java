package epic_energy.BW5_Team3.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(UUID userId) {
        super("The id: " + userId + " - has not been found!");
    }
}
