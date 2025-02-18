package com.springboot.jwt.resume.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.entity.ResultStatus;
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

    private boolean apply = false;

    public boolean isApply() {
        return apply;
    }

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private User user;

    public FrontendResume(User user) {
        this.user = user;
    }

    public ResultStatus getResultStatus() {
        return user != null && user.getResult() != null ? user.getResult().getResultStatus() : null;
    }
}