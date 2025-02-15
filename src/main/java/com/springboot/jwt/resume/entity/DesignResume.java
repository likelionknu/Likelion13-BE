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
@Table(name = "designresume")
public class DesignResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String designcontent1;

    @Column(nullable = false)
    private String designcontent2;

    @Column(nullable = false)
    private String designcontent3;

    @Column(nullable = false)
    private String designcontent4;

    @Column(nullable = false)
    private String designcontent5;

    @Column(nullable = false)
    private String designcontent6;

    @Column(nullable = false)
    private String designcontent7;

    @Column(nullable = false)
    private String designcontent8;

    @Column(nullable = false)
    private String designcontent9;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private User user;
}