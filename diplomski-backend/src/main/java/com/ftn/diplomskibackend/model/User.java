package com.ftn.diplomskibackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Boolean isAdministrator;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LessonProgress> lessonProgresses;
}
