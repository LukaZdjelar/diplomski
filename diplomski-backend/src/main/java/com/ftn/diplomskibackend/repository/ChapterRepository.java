package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAll();
    Optional<Chapter> findById(Long id);
}
