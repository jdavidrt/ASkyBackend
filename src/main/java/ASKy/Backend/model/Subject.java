package ASKy.Backend.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectsId;

    @Column(nullable = false, length = 45)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    // Getters and Setters
    public Integer getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(Integer subjectsId) {
        this.subjectsId = subjectsId;
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectsId, subject.subjectsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectsId);
    }
}