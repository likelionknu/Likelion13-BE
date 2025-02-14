package com.springboot.jwt.result.service;

import com.springboot.jwt.login.entity.User;
import com.springboot.jwt.login.repository.UserRepository;
import com.springboot.jwt.result.entity.Result;
import com.springboot.jwt.result.entity.ResultStatus;
import com.springboot.jwt.result.repository.ResultRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("[error] 해당 studentId에 맞는 유저를 찾을 수 없습니다."));

        Result result = resultRepository.findByUser(user)
                .orElse(new Result());

        result.setResultStatus(resultStatus);
        result.setUser(user);
        result.setComment(comment);

        return resultRepository.save(result);
    }



}