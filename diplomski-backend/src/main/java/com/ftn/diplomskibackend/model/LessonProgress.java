package com.ftn.diplomskibackend.model;

import com.ftn.diplomskibackend.model.enumeration.ELessonStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LessonProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Lesson lesson;
    @Column
    private ELessonStatus status;
}
