package com.springboot.jwt.result.dto.response;

import com.springboot.jwt.result.entity.ResultStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserListDto {

    private String name;
    private String studentId;
    private String phoneNumber;
    private String department;
    private ResultStatus resultStatus;



}
