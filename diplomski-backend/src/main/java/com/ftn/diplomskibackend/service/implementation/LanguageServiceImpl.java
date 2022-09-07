package com.ftn.diplomskibackend.service.implementation;

import com.ftn.diplomskibackend.model.Language;
import com.ftn.diplomskibackend.repository.LanguageRepository;
import com.ftn.diplomskibackend.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {
    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> findById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public Language save(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public void delete(Language language) {
        languageRepository.delete(language);
    }
}
