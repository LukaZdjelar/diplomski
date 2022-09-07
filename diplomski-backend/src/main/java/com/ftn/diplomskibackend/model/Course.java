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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToOne
    private Language local;
    @ManyToOne
    private Language foreign;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Chapter> chapters;
}
