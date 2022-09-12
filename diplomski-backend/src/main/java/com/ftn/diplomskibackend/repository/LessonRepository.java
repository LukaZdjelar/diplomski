package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.ELessonStatus;
import com.ftn.diplomskibackend.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findAll();
    Optional<Lesson> findById(Long id);
    @Query(value="select status\n" +
            "from lesson l inner join lesson_progress pr on l.id = pr.lesson_id\n" +
            "              inner join user_lesson_progresses u_pr on u_pr.lesson_progresses_id = pr.id\n" +
            "where u_pr.user_id=1 and pr.lesson_id=:lesson_id", nativeQuery = true)
    ELessonStatus getLessonStatus(@Param("lesson_id") Long lesson_id);
}
