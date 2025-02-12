package com.springboot.jwt.result.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseResultDto {
    private Long code;
    private String msg;
}
