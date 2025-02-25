package ASKy.Backend.specification;

import ASKy.Backend.model.Question;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuestionSpecification {


    // 🔹 Version 2: Filters by topic ID, user ID, expert ID
    public static Specification<Question> byFilters(String title, String body, Integer topicId, Integer userId, Integer expertId, Byte status, String orderBy) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Filter by Title
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            // 🔹 Filter by Body Content
            if (body != null && !body.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("body")), "%" + body.toLowerCase() + "%"));
            }

            // 🔹 Filter by Topic ID
            if (topicId != null) {
                predicates.add(criteriaBuilder.equal(root.get("topic").get("topicId"), topicId));
            }

            // 🔹 Filter by User ID
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("userId"), userId));
            }

            // 🔹 Filter by Expert ID
            if (expertId != null) {
                predicates.add(criteriaBuilder.equal(root.get("expert").get("userId"), expertId));
            }

            // 🔹 Filter by Status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // 🔹 Sorting Logic
            applySorting(query, criteriaBuilder, root, orderBy);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // 🔹 Helper method for Sorting
    private static void applySorting(jakarta.persistence.criteria.CriteriaQuery<?> query, jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder, jakarta.persistence.criteria.Root<Question> root, String orderBy) {
        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("price")) {
                query.orderBy(criteriaBuilder.asc(root.get("price")));
            } else if (orderBy.equalsIgnoreCase("createdAt")) {
                query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
            }
        } else {
            // Default Sorting: Latest questions first
            query.orderBy(criteriaBuilder.desc(root.get("createdAt")));
        }
    }
}
