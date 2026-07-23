package epic_energy.BW5_Team3.specifications;

import epic_energy.BW5_Team3.entities.Invoice;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.UUID;

public class InvoiceSpecification {

    //  Filtro per cliente
    public static Specification<Invoice> hasClient(UUID clientId) {
        return (root, query, criteriaBuilder) ->
                clientId == null ? null : criteriaBuilder.equal(root.get("client").get("clientId"), clientId);
    }

    //  Filtro per stato (es. "PAID", "PENDING", "OVERDUE")
    public static Specification<Invoice> hasStatus(Integer invoiceStatusId) {
        return (root, query, criteriaBuilder) ->
                invoiceStatusId == null ? null : criteriaBuilder.equal(root.get("invoiceStatus").get("invoiceStatusId"), invoiceStatusId);
    }

    //  Filtro per data esatta
    public static Specification<Invoice> hasDate(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                date == null ? null : criteriaBuilder.equal(root.get("date"), date);
    }

    //  Filtro per anno (range 1 gennaio - 31 dicembre)
    public static Specification<Invoice> hasYear(Integer year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) return null;
            LocalDate inizioAnno = LocalDate.of(year, 1, 1);
            LocalDate fineAnno = LocalDate.of(year, 12, 31);
            return criteriaBuilder.between(root.get("date"), inizioAnno, fineAnno);
        };
    }

    //  Filtro per range di importi
    public static Specification<Invoice> hasAmountBetween(Double min, Double max) {
        return (root, query, criteriaBuilder) -> {
            if (min == null && max == null) return null;
            if (min == null) return criteriaBuilder.lessThanOrEqualTo(root.get("amount"), max);
            if (max == null) return criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), min);
            return criteriaBuilder.between(root.get("amount"), min, max);
        };
    }
}
