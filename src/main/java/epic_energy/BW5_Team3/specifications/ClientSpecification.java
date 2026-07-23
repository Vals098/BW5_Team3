package epic_energy.BW5_Team3.specifications;

import epic_energy.BW5_Team3.entities.Client;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClientSpecification {

    public static Specification<Client> hasLegalName(String legalName) {
        return ((root, query, criteriaBuilder) ->
                legalName == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("legalName")), "%" + legalName.toLowerCase() + "%"));
    }

    public static Specification<Client> isLowerThanYearIncome(BigDecimal maxYearlyIncome) {
        return (((root, query, criteriaBuilder) ->
                maxYearlyIncome == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("yearlyIncome"), maxYearlyIncome)));
    }

    public static Specification<Client> isBeforeThanEntryDate(LocalDate entryDate) {
        return (((root, query, criteriaBuilder) ->
                entryDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("entryDate"), entryDate)));
    }

    public static Specification<Client> isBeforeThanLastContactDate(LocalDate lastContactDate) {
        return (((root, query, criteriaBuilder) ->
                lastContactDate == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("LastContact"), lastContactDate)));
    }

}
