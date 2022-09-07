package com.ftn.diplomskibackend.service.implementation;

import com.ftn.diplomskibackend.model.LessonProgress;
import com.ftn.diplomskibackend.repository.LessonProgressRepository;
import com.ftn.diplomskibackend.service.LessonProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonProgressServiceImpl implements LessonProgressService {
    @Autowired
    LessonProgressRepository lessonProgressRepository;

    @Override
    public List<LessonProgress> findAll() {
        return lessonProgressRepository.findAll();
    }

    @Override
    public Optional<LessonProgress> findById(Long id) {
        return lessonProgressRepository.findById(id);
    }

    @Override
    public LessonProgress save(LessonProgress lessonProgress) {
        return lessonProgressRepository.save(lessonProgress);
    }

    @Override
    public void delete(LessonProgress lessonProgress) {
        lessonProgressRepository.delete(lessonProgress);
    }
}
