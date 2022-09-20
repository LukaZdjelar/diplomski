package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();
    Optional<Course> findById(Long id);
    @Query(value = "select *\n" +
            "from course\n" +
            "where foreign_id=:foreignId and local_id=:localId", nativeQuery = true)
    Course courseExists(@Param("localId") Long localId, @Param("foreignId") Long foreignId);
    @Query(value = "select count(cc.chapters_id)\n" +
            "from course c inner join course_chapters cc on c.id = cc.course_id\n" +
            "where c.id=:courseId", nativeQuery = true)
    Integer countChaptersByCourse(@Param("courseId") Long courseId);
}
