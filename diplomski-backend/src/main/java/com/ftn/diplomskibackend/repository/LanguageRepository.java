package com.ftn.diplomskibackend.repository;

import com.ftn.diplomskibackend.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findAll();
    Optional<Language> findById(Long id);
}
