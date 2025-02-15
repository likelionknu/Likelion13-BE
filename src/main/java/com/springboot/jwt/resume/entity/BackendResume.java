package com.springboot.jwt.resume.entity;

import com.springboot.jwt.login.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "backendresume")
public class BackendResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String backendcontent1;

    @Column(nullable = false)
    private String backendcontent2;

    @Column(nullable = false)
    private String backendcontent3;

    @Column(nullable = false)
    private String backendcontent4;

    @Column(nullable = false)
    private String backendcontent5;

    @Column(nullable = false)
    private String backendcontent6;

    @Column(nullable = false)
    private String backendcontent7;

    @Column(nullable = false)
    private String backendcontent8;

    @Column(nullable = false)
    private String backendcontent9;

    @Column(nullable = false)
    private String backendcontent10;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private User user;
}