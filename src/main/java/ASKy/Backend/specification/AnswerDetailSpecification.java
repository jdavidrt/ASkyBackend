package ASKy.Backend.specification;
import ASKy.Backend.model.AnswerDetail;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnswerDetailSpecification {

    public static Specification<AnswerDetail> byFilters(String expertName, String userName, Boolean isRight, Integer minRating) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Join to Expert and User to enable filtering by name
            Join<Object, Object> expertJoin = root.join("expert");
            Join<Object, Object> userJoin = root.join("user");
            Join<Object, Object> answerJoin = root.join("answer");

            // 🔹 Filter by Expert Name
            if (expertName != null && !expertName.isEmpty()) {
                String namePattern = "%" + expertName.toLowerCase() + "%";
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(expertJoin.get("firstName")), namePattern);
                Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(expertJoin.get("lastName")), namePattern);
                predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
            }

            // 🔹 Filter by User Name
            if (userName != null && !userName.isEmpty()) {
                String namePattern = "%" + userName.toLowerCase() + "%";
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("firstName")), namePattern);
                Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("lastName")), namePattern);
                predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
            }

            // 🔹 Filter by Correctness of Answer
            if (isRight != null) {
                predicates.add(criteriaBuilder.equal(root.get("isRight"), isRight));
            }

            // 🔹 Filter by Minimum Rating
            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(answerJoin.get("rating"), minRating));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}