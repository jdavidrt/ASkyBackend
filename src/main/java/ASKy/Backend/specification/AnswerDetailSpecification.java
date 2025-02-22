package ASKy.Backend.specification;
import ASKy.Backend.model.AnswerDetail;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnswerDetailSpecification {

    public static Specification<AnswerDetail> byFilters(Integer expertId, Integer userId, Boolean isRight) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Filter by Expert ID
            if (expertId != null) {
                predicates.add(criteriaBuilder.equal(root.get("expert").get("userId"), expertId));
            }

            // 🔹 Filter by User ID
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("userId"), userId));
            }

            // 🔹 Filter by Correctness of Answer
            if (isRight != null) {
                predicates.add(criteriaBuilder.equal(root.get("isRight"), isRight));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}