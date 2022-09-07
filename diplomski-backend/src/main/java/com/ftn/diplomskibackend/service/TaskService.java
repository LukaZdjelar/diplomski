package com.ftn.diplomskibackend.service;

import com.ftn.diplomskibackend.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
    void delete(Task task);
}
