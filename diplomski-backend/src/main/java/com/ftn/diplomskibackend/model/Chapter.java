package com.ftn.diplomskibackend.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
//@Table(name = "chapters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer level;
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Lesson> lessons;
    @ManyToOne
    private Course course;
}
