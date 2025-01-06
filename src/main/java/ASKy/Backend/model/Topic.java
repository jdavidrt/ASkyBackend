package ASKy.Backend.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicsId;

    @Column(nullable = false, length = 45)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "subjects_subjectsId", nullable = false)
    private Subject subject;

    // Getters and Setters
    public Integer getTopicsId() {
        return topicsId;
    }

    public void setTopicsId(Integer topicsId) {
        this.topicsId = topicsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(topicsId, topic.topicsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topicsId);
    }
}