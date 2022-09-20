package com.ftn.diplomskibackend.service;

import com.ftn.diplomskibackend.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Course course);
    Course courseExists(Long localId, Long foreignId);
    Integer countChaptersByCourse(Long courseId);
}
