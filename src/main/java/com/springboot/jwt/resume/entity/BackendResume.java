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

    private boolean apply = false;
    public boolean isApply() {
        return apply;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private User user;

    public BackendResume(User user) {
        this.user = user;
    }

    public ResultStatus getResultStatus() {
        return user != null && user.getResult() != null ? user.getResult().getResultStatus() : null;
    }
}