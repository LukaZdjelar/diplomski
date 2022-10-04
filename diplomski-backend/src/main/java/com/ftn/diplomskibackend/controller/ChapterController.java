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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/chapters")
@CrossOrigin
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "/course/{id}")
    public ResponseEntity<List<ChapterDTO>> getByCourse(@PathVariable Long id){
        Course course = courseService.findById(id).orElse(null);
        if (course!=null){
            List<Chapter> chapters = course.getChapters();
            return new ResponseEntity<>(ChapterMapper.mapListToDTO(chapters), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<ChapterDTO> create(@RequestBody ChapterDTO chapterDTO){
        chapterDTO.setLessons(new ArrayList<>());
        if (chapterDTO.getName().equals("") || chapterDTO.getLevel()<1 || chapterDTO.getCourseId() == null ||
                chapterDTO.getLevel() > courseService.countChaptersByCourse(chapterDTO.getCourseId())+1 || chapterDTO.getId()!=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            if (chapterDTO.getLevel()<=courseService.countChaptersByCourse(chapterDTO.getCourseId())){
                chapterService.adjustLevels(chapterDTO.getLevel(), chapterDTO.getCourseId());
            }
            Chapter chapter = ChapterMapper.mapModel(chapterDTO);
            chapterService.save(chapter);
            Course course = courseService.findById(chapterDTO.getCourseId()).orElse(null);
            course.getChapters().add(chapter);
            courseService.save(course);
            return new ResponseEntity<>(chapterDTO, HttpStatus.CREATED);
        }
    }
    @PutMapping
    public ResponseEntity<ChapterDTO> update(@RequestBody ChapterDTO chapterDTO) {
        if (chapterDTO.getName().equals("") || chapterDTO.getLevel() < 1 ||
                chapterDTO.getLevel() > courseService.countChaptersByCourse(chapterDTO.getCourseId()) + 1 || chapterDTO.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (chapterDTO.getLevel() <= courseService.countChaptersByCourse(chapterDTO.getCourseId())) {
                chapterService.adjustLevels(chapterDTO.getLevel(), chapterDTO.getCourseId());
            }
            Chapter chapter = ChapterMapper.mapModel(chapterDTO);
            chapterService.save(chapter);
            return new ResponseEntity<>(chapterDTO, HttpStatus.CREATED);
        }
    }
}
