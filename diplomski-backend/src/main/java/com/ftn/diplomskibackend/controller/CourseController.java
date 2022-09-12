package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.CourseMapper;
import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.model.dto.CourseDTO;
import com.ftn.diplomskibackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAll(){
        List<Course> courses = courseService.findAll();
        return new ResponseEntity<>(CourseMapper.mapListToDTO(courses), HttpStatus.OK);
    }

}
