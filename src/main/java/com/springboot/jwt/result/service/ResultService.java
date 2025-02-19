package com.springboot.jwt.result.service;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.entity.ResultStatus;
import com.springboot.jwt.result.repository.ResultRepository;
import com.springboot.jwt.resume.entity.BackendResume;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;

    public ResultService(ResultRepository resultRepository, UserRepository userRepository) {
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
    }

    // 합불 부여
    public Result assignResult(String studentId, ResultStatus resultStatus, String comment) {
        User user = userRepository.findByStudentId(studentId)
                .filter(User::isApply)
                .orElseThrow(() -> new IllegalArgumentException("[error] 해당 학번 사용자가 존재하지 않습니다. 지원서 최종 제출 후 다시 시도해주세요."));

        Result result = resultRepository.findByUser(user)
                .orElse(new Result());

        result.setResultStatus(resultStatus);
        result.setUser(user);
        result.setComment(comment);

        return resultRepository.save(result);
    }



}