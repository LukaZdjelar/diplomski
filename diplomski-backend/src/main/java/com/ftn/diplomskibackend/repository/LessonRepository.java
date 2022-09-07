package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAll();
    Optional<Lesson> findById(Long id);
}
