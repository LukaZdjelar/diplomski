package com.ftn.diplomskibackend.service.implementation;

import com.ftn.diplomskibackend.model.Chapter;
import com.ftn.diplomskibackend.repository.ChapterRepository;
import com.ftn.diplomskibackend.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterRepository chapterRepository;

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public Optional<Chapter> findById(Long id) {
        return chapterRepository.findById(id);
    }

    @Override
    public Chapter save(Chapter chapter) {
        return chapterRepository.save(chapter);
    }

    @Override
    public void delete(Chapter chapter) {
        chapterRepository.delete(chapter);
    }

    @Override
    public Integer countCompletedLessons(Long user_id, Long chapter_id) {
        return chapterRepository.countCompletedLessons(user_id, chapter_id);
    }

    @Override
    public void adjustLevels(Integer level, Long courseId) {
        for (Chapter chapter: chapterRepository.chaptersLevelsToBeAdjusted(level, courseId)) {
            chapter.setLevel(chapter.getLevel()+1);
            save(chapter);
        }
    }
}
