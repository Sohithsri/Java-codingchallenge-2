package com.taskAPI.service;

import com.taskAPI.dto.TaskStatisticsDTO;
import com.taskAPI.entities.Task;
import com.taskAPI.entities.User;
import com.taskAPI.exception.ResourceNotFoundException;
import com.taskAPI.respository.TaskRepository;
import com.taskAPI.respository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long taskId, Task updatedTask) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with","Id",taskId));
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setStatus(updatedTask.getStatus());
        if ("Completed".equals(updatedTask.getStatus())) {
            task.setCompletedDate(LocalDate.now());
        }
        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with","Id",taskId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with","Id",userId));
        task.setAssignedUser(user);
        return taskRepository.save(task);
    }

    public Task setTaskProgress(Long taskId, Integer progress){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with","Id",taskId));
        task.setProgress(progress);
        return taskRepository.save(task);
    }

    public List<Task> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return taskRepository.findByDueDateBefore(today);
    }

    public List<Task> getAllTasksByStatus(String status){
        return taskRepository.findByStatus(status);
    }

    public List<Task> getAllTasksAssignedToUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with","Id",userId));
        return taskRepository.findByAssignedUser(user);
    }

    public List<Task> getCompletedTasksBetweenDates(LocalDate startDate, LocalDate endDate){
        return taskRepository.findByCompletedDateBetween(startDate, endDate);
    }
    public TaskStatisticsDTO getTaskStatistics() {
        long totalTasks = taskRepository.count();
        long completedTasks = taskRepository.countByStatus("Completed");
        double percentageCompleted = 0.0;
        if (totalTasks > 0) {
            percentageCompleted = (double) completedTasks / totalTasks * 100;
        }
        return new TaskStatisticsDTO(totalTasks, completedTasks, percentageCompleted);
    }
}
