package com.springboot.jwt.result.entity;


import com.springboot.jwt.login.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "result")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus resultStatus;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

    // 한줄평
    private String comment;

    public Result(ResultStatus resultStatus, User user, String comment) {
        this.resultStatus = resultStatus;
        this.user = user;
        this.comment = comment;
}
}
