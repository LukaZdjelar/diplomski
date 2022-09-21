package com.ftn.diplomskibackend.controller;

import com.ftn.diplomskibackend.mapper.LanguageMapper;
import com.ftn.diplomskibackend.model.Language;
import com.ftn.diplomskibackend.model.dto.LanguageDTO;
import com.ftn.diplomskibackend.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/languages")
public class LanguageController {
    @Autowired
    LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageDTO>> getAll(){
        List<Language> languages = languageService.findAll();
        return new ResponseEntity<>(LanguageMapper.mapListToDTO(languages), HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<LanguageDTO> getOne(@PathVariable Long id){
        Language language = languageService.findById(id).orElse(null);
        if (language!=null){
            return new ResponseEntity<>(LanguageMapper.mapDTO(language), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping
    public ResponseEntity<LanguageDTO> create(@RequestBody LanguageDTO languageDTO){
        if (languageDTO.getName().equals("") || languageDTO.getId()!=null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            languageService.save(LanguageMapper.mapModel(languageDTO));
            return new ResponseEntity<>(languageDTO, HttpStatus.CREATED);
        }
    }
    @PutMapping
    public ResponseEntity<LanguageDTO> update(@RequestBody LanguageDTO languageDTO){
        if (languageDTO.getName().equals("") || languageDTO.getId()==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            languageService.save(LanguageMapper.mapModel(languageDTO));
            return new ResponseEntity<>(languageDTO, HttpStatus.CREATED);
        }
    }
}
