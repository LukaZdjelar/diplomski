package com.ftn.diplomskibackend.service;


import com.ftn.diplomskibackend.model.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {
    List<Lesson> findAll();
    Optional<Lesson> findById(Long id);
    Lesson save(Lesson lesson);
    void delete(Lesson lesson);
}
