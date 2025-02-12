package com.springboot.jwt.result.controller;

import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.login.service.UserService;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.entity.ResultStatus;
import com.springboot.jwt.result.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService){
        this.resultService = resultService;
    }

    @PostMapping("/result/{studentId}")
    public ResponseEntity<Result> assignResult(@PathVariable String studentId, @RequestParam ResultStatus resultStatus, @RequestParam(required = false) String comment) {
        Result result = resultService.assignResult(studentId, resultStatus, comment);
        return ResponseEntity.ok(result);
    }
}
