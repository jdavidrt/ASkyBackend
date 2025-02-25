package ASKy.Backend.specification;

import ASKy.Backend.model.Answer;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AnswerSpecification {

    public static Specification<Answer> byFilters(String expertName, String userName, Boolean isRight, Integer minRating) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Join to Question to access Expert and User
            Join<Object, Object> questionJoin = root.join("question");
            Join<Object, Object> expertJoin = questionJoin.join("expert");
            Join<Object, Object> userJoin = questionJoin.join("user");

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
                predicates.add(criteriaBuilder.equal(root.get("type"), isRight ? (byte) 1 : (byte) 0));
            }

            // 🔹 Filter by Minimum Rating (Only if Answer has a rating)
            if (minRating != null) {
                Join<Object, Object> ratingJoin = root.join("rating", jakarta.persistence.criteria.JoinType.LEFT);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(ratingJoin.get("rating"), minRating));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
