package com.springboot.jwt.result.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.resume.entity.BackendResume;
import com.springboot.jwt.resume.entity.DesignResume;
import com.springboot.jwt.resume.entity.FrontendResume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "result")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus resultStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "studentId", nullable = false)
    @JsonIgnore
    private User user;

    private String comment; // 한줄평

    public Result(ResultStatus resultStatus, User user, String comment) {
        this.resultStatus = resultStatus;
        this.user = user;
        this.comment = comment;
    }
}