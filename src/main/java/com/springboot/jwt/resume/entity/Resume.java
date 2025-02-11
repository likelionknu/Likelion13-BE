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
@Table(name = "resume")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content1;

    @Column(nullable = false)
    private String content2;

    @Column(nullable = false)
    private String content3;

    @Column(nullable = false)
    private String content4;

    @Column(nullable = false)
    private String content5;

    @Column(nullable = false)
    private String content6;

    @Column(nullable = false)
    private String content7;

    @Column(nullable = false)
    private String content8;

    @Column(nullable = false)
    private String content9;

    @Column(nullable = false)
    private String content10;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;
}