package epic_energy.BW5_Team3.specifications;

import epic_energy.BW5_Team3.entities.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasLegalName(String legalName) {
        return ((root, query, criteriaBuilder) ->
                legalName == null ? null : criteriaBuilder.equal(root.get("legalName"), legalName));
    }

}
