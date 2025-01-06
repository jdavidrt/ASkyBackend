package ASKy.Backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer answersId;

    @Lob
    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "questionId", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private Integer rating;

    @Lob
    private String comment;

    private LocalDateTime ratedAt;

    // Getters and Setters
    public Integer getAnswersId() {
        return answersId;
    }

    public void setAnswersId(Integer answersId) {
        this.answersId = answersId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(LocalDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(answersId, answer.answersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answersId);
    }

}
