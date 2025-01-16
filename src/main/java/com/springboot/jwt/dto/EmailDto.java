package com.springboot.jwt.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class EmailDto {
    private String email;
    private String verifyCode;

}
