package Itstime.planear.statusmessage.domain;

import Itstime.planear.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class StatusMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private MessageType messageType;


    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

//    @Column(name = "uncomplete_count")
//    private Integer uncompleteCount;
//
//    @Column(name = "achievement_rate")
//    private Integer achievementRate;

    @CreationTimestamp
    @Column(name = "created_ts")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;
}
