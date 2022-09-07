package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    List<LessonProgress> findAll();
    Optional<LessonProgress> findById(Long id);
}
