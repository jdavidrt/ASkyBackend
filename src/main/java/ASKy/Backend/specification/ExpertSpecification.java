package ASKy.Backend.specification;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.ExpertsTopics;
import ASKy.Backend.model.Subject;
import ASKy.Backend.model.Topic;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ExpertSpecification {

    public static Specification<Expert> byFilters(ExpertFilterRequest filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔹 Filtrar por nombre (concatenar nombre y apellido con LIKE)
            if (filters.getName() != null && !filters.getName().isEmpty()) {
                String namePattern = "%" + filters.getName().toLowerCase() + "%";
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), namePattern);
                Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), namePattern);
                predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
            }

            // 🔹 Filtrar por tema (topic)
            if (filters.getTopic() != null && !filters.getTopic().isEmpty()) {
                Join<Expert, ExpertsTopics> expertTopicsJoin = root.join("expertTopics");
                Join<ExpertsTopics, Topic> topicJoin = expertTopicsJoin.join("topic");
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(topicJoin.get("name")), "%" + filters.getTopic().toLowerCase() + "%"));
            }

            // 🔹 Filtrar por subtema (subject)
            if (filters.getSubtopic() != null && !filters.getSubtopic().isEmpty()) {
                Join<Expert, ExpertsTopics> expertTopicsJoin = root.join("expertTopics");
                Join<ExpertsTopics, Topic> topicJoin = expertTopicsJoin.join("topic");
                Join<Topic, Subject> subjectJoin = topicJoin.join("subtopics"); // 🔹 Ahora se une correctamente con `subtopics`
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(subjectJoin.get("name")), "%" + filters.getSubtopic().toLowerCase() + "%"));
            }

            // 🔹 Ordenamiento dinámico
            if (filters.getOrderBy() != null) {
                if (filters.getOrderBy().equalsIgnoreCase("rating")) {
                    query.orderBy(criteriaBuilder.desc(root.get("averageRating")));
                } else if (filters.getOrderBy().equalsIgnoreCase("price")) {
                    query.orderBy(criteriaBuilder.asc(root.get("basePrice")));
                }
            }

            // 🔹 Exclude sanctioned experts
            predicates.add(criteriaBuilder.isFalse(root.get("sanctioned")));

            // 🔹 Dynamic Sorting (Default: highest rating + highest response count)
            if (filters.getOrderBy() != null) {
                if (filters.getOrderBy().equalsIgnoreCase("rating")) {
                    query.orderBy(criteriaBuilder.desc(root.get("averageRating")));
                } else if (filters.getOrderBy().equalsIgnoreCase("price")) {
                    query.orderBy(criteriaBuilder.asc(root.get("basePrice")));
                }
            } else {
                query.orderBy(
                        criteriaBuilder.desc(root.get("averageRating")),
                        criteriaBuilder.desc(root.get("totalResponses"))
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
