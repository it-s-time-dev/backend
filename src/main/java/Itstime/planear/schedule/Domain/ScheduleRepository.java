package Itstime.planear.schedule.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    Optional<Schedule> findById(Long id);
}