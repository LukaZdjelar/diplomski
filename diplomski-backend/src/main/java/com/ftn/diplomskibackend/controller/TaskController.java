package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.TaskMapper;
import com.ftn.diplomskibackend.model.Lesson;
import com.ftn.diplomskibackend.model.Task;
import com.ftn.diplomskibackend.model.dto.TaskDTO;
import com.ftn.diplomskibackend.service.LessonService;
import com.ftn.diplomskibackend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAll(){
        List<Task> tasks = taskService.findAll();
        return new ResponseEntity<>(TaskMapper.mapListToDTO(tasks), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDTO> findOne(@PathVariable Long id){
        Task task = taskService.findById(id).orElse(null);
        if (task!=null){
            return new ResponseEntity<>(TaskMapper.mapDTO(task),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/lesson/{id}")
    public ResponseEntity<List<TaskDTO>> findByLesson(@PathVariable Long id){
        Lesson lesson = lessonService.findById(id).orElse(null);
        if (lesson!=null){
            return new ResponseEntity<>(TaskMapper.mapListToDTO(lesson.getTasks()),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO){
        if (taskDTO.getQuestion().equals("") || taskDTO.getAnswer().equals("") || taskDTO.getId()!=null || taskDTO.getLessonId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            Task task = TaskMapper.mapModel(taskDTO);
            taskService.save(task);
            Lesson lesson = lessonService.findById(taskDTO.getLessonId()).orElse(null);
            lesson.getTasks().add(task);
            lessonService.save(lesson);
            return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
        }
    }
}
