package com.springboot.jwt.result.controller;

import com.springboot.jwt.result.dto.response.ResponseUserListDto;
import com.springboot.jwt.result.service.UserListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserListController {

    private final UserListService userListService;

    @GetMapping("/submit")
    public ResponseEntity<List<ResponseUserListDto>>getUserList(){
        List<ResponseUserListDto> userList = userListService.getUserList();
        return ResponseEntity.ok(userList);
    }
}
