package com.ftn.diplomskibackend.mapper;

import com.ftn.diplomskibackend.model.Chapter;
import com.ftn.diplomskibackend.model.dto.ChapterDTO;
import com.ftn.diplomskibackend.service.ChapterService;
import com.ftn.diplomskibackend.service.implementation.ChapterServiceImpl;
import com.ftn.diplomskibackend.util.SpringContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ChapterMapper {
    private static ChapterService getChapterService() {
        return SpringContext.getBean(ChapterService.class);
    }

    public static Chapter mapModel(ChapterDTO chapterDTO){
        return Chapter.builder()
                .id(chapterDTO.getId())
                .name(chapterDTO.getName())
                .level(chapterDTO.getLevel())
                .lessons(LessonMapper.mapListToModel(chapterDTO.getLessons()))
                .build();
    }
    public static ChapterDTO mapDTO(Chapter chapter){
        return ChapterDTO.builder()
                .id(chapter.getId())
                .name(chapter.getName())
                .level(chapter.getLevel())
                .lessons(LessonMapper.mapListToDTO(chapter.getLessons()))
                .isLocked(getChapterService().countCompletedLessons(1L, chapter.getId())<chapter.getLessons().size())
                .build();
    }
    public static List<Chapter> mapListToModel(List<ChapterDTO> chapters){
        List<Chapter> list = chapters.stream()
                .map(chapter -> mapModel(chapter))
                .collect(Collectors.toList());
        return list;
    }
    public static List<ChapterDTO> mapListToDTO(List<Chapter> chapters){
        List<ChapterDTO> dtoList = chapters.stream()
                .map(chapter -> mapDTO(chapter))
                .collect(Collectors.toList());
        return dtoList;
    }
}
