package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.CourseMapper;
import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.model.dto.CourseDTO;
import com.ftn.diplomskibackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/courses")
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}