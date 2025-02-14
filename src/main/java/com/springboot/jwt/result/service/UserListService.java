package com.springboot.jwt.result.service;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.result.dto.response.ResponseUserListDto;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserListService {
    private final ResultRepository resultRepository;

    public List<ResponseUserListDto> getUserList() {
        return resultRepository.findAll().stream()
                .map(result -> new ResponseUserListDto(
                        result.getUser().getName(),
                        result.getUser().getStudentId(),
                        result.getUser().getPhone(),
                        result.getUser().getDepartment(),
                        result.getResultStatus()
                ))
                .collect(Collectors.toList());
    }
}