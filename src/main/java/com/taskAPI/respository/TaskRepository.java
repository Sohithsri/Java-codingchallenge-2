package com.taskAPI.respository;

import com.taskAPI.entities.Task;
import com.taskAPI.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUser(User user);
    List<Task> findByStatus(String status);
    List<Task> findByCompletedDateBetween(LocalDate start, LocalDate end);
    List<Task> findByDueDateBefore(LocalDate date);
    Long countByStatus(String status);

}
