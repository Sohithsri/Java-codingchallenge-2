package com.taskAPI.controller;

import com.taskAPI.dto.TaskAssignDTO;
import com.taskAPI.dto.TaskCreateDTO;
import com.taskAPI.dto.TaskProgressDTO;
import com.taskAPI.dto.TaskStatisticsDTO;
import com.taskAPI.entities.Task;
import com.taskAPI.service.TaskService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;


    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateDTO taskDTO) {
        Task task = toEntity(taskDTO);
        return ResponseEntity.ok(taskService.createTask(task));
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId, @RequestBody Task task) {
        return ResponseEntity.ok(taskService.updateTask(taskId, task));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
       return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping("/{taskId}/assign")
    public ResponseEntity<Task> taskAssigningToUser(@PathVariable Long taskId, @RequestBody TaskAssignDTO taskAssignDTO){
        return ResponseEntity.ok(taskService.assignTaskToUser(taskId, taskAssignDTO.getUserId()));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> getAllAssignedTasksToUser(@PathVariable Long userId){
        return ResponseEntity.ok(taskService.getAllTasksAssignedToUser(userId));
    }

    @PutMapping("/{taskId}/progress")
    public ResponseEntity<Void> setTaskProgress(@PathVariable Long taskId, @RequestBody TaskProgressDTO progressDTO) {
        taskService.setTaskProgress(taskId, progressDTO.getProgress());
        return (ResponseEntity<Void>) ResponseEntity.ok();
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Task>> getOverdueTasks() {
        return ResponseEntity.ok(taskService.getOverdueTasks());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status){
        return ResponseEntity.ok(taskService.getAllTasksByStatus(status));
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getAllCompletedTasks(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        return ResponseEntity.ok(taskService.getCompletedTasksBetweenDates(startDate,endDate));
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatisticsDTO> getTaskStatistics(){
        return ResponseEntity.ok(taskService.getTaskStatistics());
    }

    private Task toEntity(TaskCreateDTO taskCreateDTO){
        Task task = new Task();

        task.setDescription(taskCreateDTO.getDescription());
        task.setTitle(taskCreateDTO.getTitle());
        task.setDueDate(taskCreateDTO.getDueDate());
        return task;
    }

}
