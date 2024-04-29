package com.taskAPI.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private String status; // "New", "In Progress", "Completed"
    private Integer progress;
    private String priority; // "High", "Medium", "Low"

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedUser;
}
