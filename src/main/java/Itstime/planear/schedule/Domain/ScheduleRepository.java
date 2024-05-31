package Itstime.planear.schedule.Domain;
import Itstime.planear.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findAllByMemberAndStartBetween(Member member, LocalDate startInclusive, LocalDate endInclusive);

}