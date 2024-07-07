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

    @CreationTimestamp
    @Column(name = "created_ts")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    public static StatusMessage uncomplete(Member member) {
        return StatusMessage.builder()
                .messageType(MessageType.UNCOMPLETE)
                .member(member)
                .build();
    }

    public static StatusMessage qna(Member member) {
        return StatusMessage.builder()
                .messageType(MessageType.QNA)
                .member(member)
                .build();
    }

    public static StatusMessage todaySchedule(Member member) {
        return StatusMessage.builder()
                .messageType(MessageType.TODAY_SCHEDULE)
                .member(member)
                .build();
    }
}
