package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.CourseMapper;
import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.model.dto.CourseDTO;
import com.ftn.diplomskibackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/courses")
@CrossOrigin
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAll(){
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(CourseMapper.mapListToDTO(courses), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> getOne(@PathVariable Long id){
        Course course = courseService.findById(id).orElse(null);
        if (course!=null){
            return new ResponseEntity<>(CourseMapper.mapDTO(course), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO){
        courseDTO.setChapters(new ArrayList<>());
        if (courseDTO.getName().equals("") || courseDTO.getLocal() == null || courseDTO.getForeign() == null ||
                courseDTO.getLocal().getId().equals(courseDTO.getForeign().getId()) || courseDTO.getId() != null ||
                courseService.courseExists(courseDTO.getLocal().getId(), courseDTO.getForeign().getId()) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            courseService.save(CourseMapper.mapModel(courseDTO));
            return new ResponseEntity<>(courseDTO ,HttpStatus.CREATED);
        }
    }
    @PutMapping
    public ResponseEntity<CourseDTO> update(@RequestBody CourseDTO courseDTO){
        if (courseDTO.getName().equals("") || courseDTO.getId() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{
            courseService.save(CourseMapper.mapModel(courseDTO));
            return new ResponseEntity<>(courseDTO ,HttpStatus.CREATED);
        }
    }
}