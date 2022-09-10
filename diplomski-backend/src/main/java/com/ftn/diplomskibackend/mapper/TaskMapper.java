package com.ftn.diplomskibackend.mapper;

import com.ftn.diplomskibackend.model.Task;
import com.ftn.diplomskibackend.model.dto.TaskDTO;

import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {
    public static Task mapModel(TaskDTO taskDTO){
        return Task.builder()
                .id(taskDTO.getId())
                .question(taskDTO.getQuestion())
                .answer(taskDTO.getAnswer())
                .build();
    }
    public static TaskDTO mapDTO(Task task){
        return TaskDTO.builder()
                .id(task.getId())
                .question(task.getQuestion())
                .answer(task.getAnswer())
                .build();
    }
    public static List<Task> mapListToModel(List<TaskDTO> tasks){
        List<Task> list = tasks.stream()
                .map(task -> mapModel(task))
                .collect(Collectors.toList());
        return list;
    }
    public static List<TaskDTO> mapListToDTO(List<Task> tasks){
        List<TaskDTO> dtoList = tasks.stream()
                .map(task -> mapDTO(task))
                .collect(Collectors.toList());
        return dtoList;
    }
}
