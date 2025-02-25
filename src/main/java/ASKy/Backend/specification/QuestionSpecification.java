package ASKy.Backend.specification;

import ASKy.Backend.model.Question;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class QuestionSpecification {

    public static Specification<Question> byFilters(String title, String body, String topic, String userName, String expertName, Byte status, String orderBy) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Filter by Title (LIKE %title%)
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            // 🔹 Filter by Body Content (LIKE %body%)
            if (body != null && !body.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("body")), "%" + body.toLowerCase() + "%"));
            }

            // 🔹 Filter by Topic Name (Instead of Topic ID)
            if (topic != null && !topic.isEmpty()) {
                Join<Object, Object> topicJoin = root.join("topic");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(topicJoin.get("name")), "%" + topic.toLowerCase() + "%"));
            }

            // 🔹 Filter by User Name (Instead of User ID)
            if (userName != null && !userName.isEmpty()) {
                Join<Object, Object> userJoin = root.join("user");
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("firstName")), "%" + userName.toLowerCase() + "%");
                Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(userJoin.get("lastName")), "%" + userName.toLowerCase() + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
            }

            // 🔹 Filter by Expert Name (Instead of Expert ID)
            if (expertName != null && !expertName.isEmpty()) {
                Join<Object, Object> expertJoin = root.join("expert");
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(expertJoin.get("firstName")), "%" + expertName.toLowerCase() + "%");
                Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(expertJoin.get("lastName")), "%" + expertName.toLowerCase() + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
            }

            // 🔹 Filter by Status
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // 🔹 Sorting Logic
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

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}