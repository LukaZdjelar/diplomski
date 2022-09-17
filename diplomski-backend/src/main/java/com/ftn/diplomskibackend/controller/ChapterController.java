package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.ChapterMapper;
import com.ftn.diplomskibackend.model.Chapter;
import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.model.dto.ChapterDTO;
import com.ftn.diplomskibackend.service.ChapterService;
import com.ftn.diplomskibackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/chapters")
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<ChapterDTO>> getAll(){
        List<Chapter> chapters = chapterService.findAll();
        return new ResponseEntity<>(ChapterMapper.mapListToDTO(chapters), HttpStatus.OK);
    }
    @GetMapping(value="/{id}")
    public ResponseEntity<ChapterDTO> getOne(@PathVariable Long id){
        Chapter chapter = chapterService.findById(id).orElse(null);
        if(chapter!=null){
            return new ResponseEntity<>(ChapterMapper.mapDTO(chapter),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/course/{id}")
    public ResponseEntity<List<ChapterDTO>> getByCourse(@PathVariable Long id){
        Course course = courseService.findById(id).orElse(null);
        if (course!=null){
            List<Chapter> chapters = course.getChapters();
            return new ResponseEntity<>(ChapterMapper.mapListToDTO(chapters), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
