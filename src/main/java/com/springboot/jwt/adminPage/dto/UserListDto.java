package com.springboot.jwt.adminPage.dto;


import com.springboot.jwt.result.entity.ResultStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListDto {

    private String name;
    private String studentId;
    private String phoneNumber;
    private String department;
    private ResultStatus resultStatus;



}
