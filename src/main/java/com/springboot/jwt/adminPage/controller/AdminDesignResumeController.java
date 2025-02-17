package com.springboot.jwt.adminPage.controller;

import com.springboot.jwt.adminPage.dto.UserListDto;
import com.springboot.jwt.adminPage.service.UserListService;
import com.springboot.jwt.resume.dto.DesignResumeRequestDto;
import com.springboot.jwt.resume.service.DesignResumeService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3000", "https://www.likelionknu.com"})
public class AdminDesignResumeController {
    private final DesignResumeService designResumeService;
    private final UserListService userListService;

    public AdminDesignResumeController(DesignResumeService designResumeService, UserListService userListService){
        this.designResumeService = designResumeService;
        this.userListService = userListService;
    }
    @GetMapping("/admin/userform/design/{studentId}")
    public ResponseEntity<DesignResumeRequestDto> showDesignResume(@RequestParam String studentId){
        DesignResumeRequestDto designResumeRequestDto = designResumeService.designGetResumeById(studentId);
        return ResponseEntity.ok(designResumeRequestDto);
    }

    @GetMapping("/admin/design/submit")
    public ResponseEntity<List<UserListDto>> getDesignUserList() {
        List<UserListDto> userList = userListService.getDesginUserList();
        return ResponseEntity.ok(userList);
    }

}
