package com.ftn.diplomskibackend.service;


import com.ftn.diplomskibackend.model.Language;

import java.util.List;
import java.util.Optional;

public interface LanguageService {
    List<Language> findAll();
    Optional<Language> findById(Long id);
    Language save(Language language);
    void delete(Language language);
}
