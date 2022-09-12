package com.ftn.diplomskibackend.service;

import com.ftn.diplomskibackend.model.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService {
    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
    Chapter save(Chapter chapter);
    void delete(Chapter chapter);
    Integer countCompletedLessons(Long user_id, Long chapter_id);
}
