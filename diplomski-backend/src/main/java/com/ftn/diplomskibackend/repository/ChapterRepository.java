package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
    @Query(value="select count(pr.status)\n" +
            "from user_lesson_progresses u_pr inner join lesson_progress pr on u_pr.lesson_progresses_id=pr.id\n" +
            "                                 inner join user u on u_pr.user_id = u.id\n" +
            "                                 inner join chapter_lessons cl on cl.lessons_id = pr.lesson_id\n" +
            "where pr.status=2 and u.id=:user_id and cl.chapter_id=:chapter_id", nativeQuery = true)
    Integer countCompletedLessons(@Param("user_id") Long user_id, @Param("chapter_id") Long chapter_id);
    @Query(value = "select c.* \n" +
            "from chapter c inner join course_chapters cc on c.id=cc.chapters_id\n" +
            "where level>=:level and cc.course_id=:courseId", nativeQuery = true)
    List<Chapter> chaptersLevelsToBeAdjusted(@Param("level") Integer level, @Param("courseId") Long courseId);
}
