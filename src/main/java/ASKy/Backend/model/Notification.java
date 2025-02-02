package ASKy.Backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "notificationId")
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Integer notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "read_status", nullable = false)
    private Byte readStatus = 0;  // 0 = unread, 1 = read

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}
