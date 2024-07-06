package Itstime.planear.statusmessage.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusTodoRepository extends JpaRepository<StatusTodo, Long> {

    List<StatusTodo> findAllByStatusMessageId(Long statusMessageId);
}
