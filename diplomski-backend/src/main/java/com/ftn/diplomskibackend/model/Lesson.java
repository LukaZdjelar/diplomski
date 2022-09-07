package com.ftn.diplomskibackend.model;

import com.ftn.diplomskibackend.model.enumeration.ELessonType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private ELessonType lessonType;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;
}
