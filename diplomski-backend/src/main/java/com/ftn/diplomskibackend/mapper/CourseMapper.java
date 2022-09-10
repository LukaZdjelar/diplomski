package com.ftn.diplomskibackend.mapper;

import com.ftn.diplomskibackend.model.Course;
import com.ftn.diplomskibackend.model.dto.CourseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class CourseMapper {
    public static Course mapModel(CourseDTO courseDTO){
        return Course.builder()
                .id(courseDTO.getId())
                .name(courseDTO.getName())
                .local(LanguageMapper.mapModel(courseDTO.getLocal()))
                .foreign(LanguageMapper.mapModel(courseDTO.getForeign()))
                .chapters(ChapterMapper.mapListToModel(courseDTO.getChapters()))
                .build();
    }
    public static CourseDTO mapDTO(Course course){
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .local(LanguageMapper.mapDTO(course.getLocal()))
                .foreign(LanguageMapper.mapDTO(course.getForeign()))
                .chapters(ChapterMapper.mapListToDTO(course.getChapters()))
                .build();
    }
    public static List<Course> mapListToModel(List<CourseDTO> courses){
        List<Course> list = courses.stream()
                .map(course -> mapModel(course))
                .collect(Collectors.toList());
        return list;
    }
    public static List<CourseDTO> mapListToDTO(List<Course> courses){
        List<CourseDTO> dtoList = courses.stream()
                .map(course -> mapDTO(course))
                .collect(Collectors.toList());
        return dtoList;
    }
}
