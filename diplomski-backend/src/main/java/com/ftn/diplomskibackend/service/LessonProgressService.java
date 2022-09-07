package com.ftn.diplomskibackend.service;


import com.ftn.diplomskibackend.model.LessonProgress;

import java.util.List;
import java.util.Optional;

public interface LessonProgressService {
    List<LessonProgress> findAll();
    Optional<LessonProgress> findById(Long id);
    LessonProgress save(LessonProgress lessonProgress);
    void delete(LessonProgress lessonProgress);
}
