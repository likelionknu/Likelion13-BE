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
@Table(name = "frontendresume")
public class FrontendResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String frontendcontent1;

    @Column(nullable = false)
    private String frontendcontent2;

    @Column(nullable = false)
    private String frontendcontent3;

    @Column(nullable = false)
    private String frontendcontent4;

    @Column(nullable = false)
    private String frontendcontent5;

    @Column(nullable = false)
    private String frontendcontent6;

    @Column(nullable = false)
    private String frontendcontent7;

    @Column(nullable = false)
    private String frontendcontent8;

    @Column(nullable = false)
    private String frontendcontent9;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private User user;
}