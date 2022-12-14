package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.LessonMapper;
import com.ftn.diplomskibackend.model.Chapter;
import com.ftn.diplomskibackend.model.Lesson;
import com.ftn.diplomskibackend.model.dto.LessonDTO;
import com.ftn.diplomskibackend.service.ChapterService;
import com.ftn.diplomskibackend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/lessons")
public class LessonController {
    @Autowired
    LessonService lessonService;
    @Autowired
    ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<LessonDTO>> getAll(){
        List<Lesson> lessons = lessonService.findAll();
        return new ResponseEntity<>(LessonMapper.mapListToDTO(lessons), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<LessonDTO> getOne(@PathVariable Long id){
        Lesson lesson = lessonService.findById(id).orElse(null);
        if (lesson!=null){
            return new ResponseEntity<>(LessonMapper.mapDTO(lesson),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/chapter/{id}")
    public ResponseEntity<List<LessonDTO>> getByChapter(@PathVariable Long id){
        Chapter chapter = chapterService.findById(id).orElse(null);
        if (chapter!=null){
            return  new ResponseEntity<>(LessonMapper.mapListToDTO(chapter.getLessons()),HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<LessonDTO> create(@RequestBody LessonDTO lessonDTO){
        lessonDTO.setTasks(new ArrayList<>());
        if (lessonDTO.getId() != null || lessonDTO.getChapterId() == null || lessonDTO.getLessonType() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            Lesson lesson = LessonMapper.mapModel(lessonDTO);
            lessonService.save(lesson);
            Chapter chapter = chapterService.findById(lessonDTO.getChapterId()).orElse(null);
            chapter.getLessons().add(lesson);
            chapterService.save(chapter);
            return new ResponseEntity<>(lessonDTO, HttpStatus.CREATED);
        }
    }
}
