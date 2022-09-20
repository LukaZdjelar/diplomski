package com.ftn.diplomskibackend.service.implementation;

import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.repository.CourseRepository;
import com.ftn.diplomskibackend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }

    @Override
    public Course courseExists(Long localId, Long foreignId) {
        return courseRepository.courseExists(localId, foreignId);
    }

    @Override
    public Integer countChaptersByCourse(Long courseId) {
        return courseRepository.countChaptersByCourse(courseId);
    }
}
