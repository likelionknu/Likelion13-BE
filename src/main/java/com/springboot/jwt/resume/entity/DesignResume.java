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
@Table(name = "designresume")
public class DesignResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent1;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent2;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent3;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent4;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent5;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent6;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent7;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent8;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String designcontent9;

    private boolean apply;

    public boolean isApply() {
        return apply;
    }

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    private User user;

    public DesignResume(User user) {
        this.user = user;
    }

    public ResultStatus getResultStatus() {
        return user != null && user.getResult() != null ? user.getResult().getResultStatus() : null;
    }
}