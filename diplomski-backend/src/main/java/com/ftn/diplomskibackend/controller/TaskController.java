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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/task")
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/lesson/{id}")
    public ResponseEntity<List<TaskDTO>> findByLesson(@PathVariable Long id){
        Lesson lesson = lessonService.findById(id).orElse(null);
        if (lesson!=null){
            return new ResponseEntity<>(TaskMapper.mapListToDTO(lesson.getTasks()),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
