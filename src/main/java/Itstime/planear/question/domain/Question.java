package Itstime.planear.question.domain;

import Itstime.planear.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Question extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "expose_start_ts")
    private LocalDateTime exposeStartAt;

    @Column(name = "expose_end_ts")
    private LocalDateTime exposeEndAt;

    public Question(String content, LocalDateTime exposeStartAt, LocalDateTime exposeEndAt) {
        this.content = content;
        this.exposeStartAt = exposeStartAt;
        this.exposeEndAt = exposeEndAt;
    }

    public boolean isExposable(LocalDateTime now) {
        return exposeStartAt.isBefore(now) && exposeEndAt.isAfter(now);
    }
}
