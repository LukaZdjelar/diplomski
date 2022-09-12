package com.ftn.diplomskibackend.mapper;

import com.ftn.diplomskibackend.model.ELessonStatus;
import com.ftn.diplomskibackend.model.Lesson;
import com.ftn.diplomskibackend.model.dto.LessonDTO;
import com.ftn.diplomskibackend.service.ChapterService;
import com.ftn.diplomskibackend.service.LessonService;
import com.ftn.diplomskibackend.util.SpringContext;

import java.util.List;
import java.util.stream.Collectors;

public class LessonMapper {
    private static LessonService getLessonService() {
        return SpringContext.getBean(LessonService.class);
    }
    public static Lesson mapModel(LessonDTO lessonDTO){
        return Lesson.builder()
                .id(lessonDTO.getId())
                .lessonType(lessonDTO.getLessonType())
                .tasks(TaskMapper.mapListToModel(lessonDTO.getTasks()))
                .build();
    }
    public static LessonDTO mapDTO(Lesson lesson){
        return LessonDTO.builder()
                .id(lesson.getId())
                .lessonType(lesson.getLessonType())
                .tasks(TaskMapper.mapListToDTO(lesson.getTasks()))
                .lessonStatus(getLessonService().getLessonStatus(lesson.getId()))
                .build();
    }
    public static List<Lesson> mapListToModel(List<LessonDTO> lessons){
        List<Lesson> list = lessons.stream()
                .map(lesson -> mapModel(lesson))
                .collect(Collectors.toList());
        return list;
    }
    public static List<LessonDTO> mapListToDTO(List<Lesson> lessons){
        List<LessonDTO> dtoList = lessons.stream()
                .map(lesson -> mapDTO(lesson))
                .collect(Collectors.toList());
        return dtoList;
    }
}
