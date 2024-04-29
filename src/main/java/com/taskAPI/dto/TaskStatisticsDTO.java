package com.taskAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStatisticsDTO {
    private long totalTasks;
    private long completedTasks;
    private double percentageCompleted;
}