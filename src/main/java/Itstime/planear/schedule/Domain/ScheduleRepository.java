package Itstime.planear.schedule.Domain;

import Itstime.planear.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByMemberAndStartBetween(Member member, LocalDate startInclusive, LocalDate endInclusive);

    @Query("SELECT s FROM Schedule s WHERE s.member = :member AND s.end >= :targetDay AND s.start < :nextDay")
    List<Schedule> findAllByMemberAndDate(Member member, LocalDate targetDay, LocalDate nextDay);

    @Query("SELECT s FROM Schedule s WHERE s.member.id IN :memberIds AND s.start BETWEEN :start AND :end")
    List<Schedule> findAllByMemberIdsAndStartBetween(@Param("memberIds") List<Long> memberIds, @Param("start") LocalDate start, @Param("end") LocalDate end);
}
