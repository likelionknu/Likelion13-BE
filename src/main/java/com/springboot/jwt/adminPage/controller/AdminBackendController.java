package com.springboot.jwt.adminPage.controller;

import com.springboot.jwt.adminPage.dto.UserListDto;
import com.springboot.jwt.adminPage.service.UserListService;
import com.springboot.jwt.resume.dto.BackendResumeRequestDto;
import com.springboot.jwt.resume.service.BackendResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3000", "https://www.likelionknu.com"})
public class AdminBackendController {

    private final BackendResumeService backendResumeService;
    private final UserListService userListService;

    @Autowired
    public AdminBackendController(BackendResumeService backendResumeService, UserListService userListService) {
        this.backendResumeService = backendResumeService;
        this.userListService = userListService;
    }

    @GetMapping("/admin/userform/backend/{id}")
    public ResponseEntity<BackendResumeRequestDto> showBackendResume(@PathVariable Long id){
        BackendResumeRequestDto backendResumeRequestDto = backendResumeService.backendGetResumeById(id);
        return ResponseEntity.ok(backendResumeRequestDto);
    }

    @GetMapping("/admin/backend/submit")
    public ResponseEntity<List<UserListDto>> getBackendUserList() {
        List<UserListDto> userList = userListService.getBackendUserList();
        return ResponseEntity.ok(userList);
    }





}
