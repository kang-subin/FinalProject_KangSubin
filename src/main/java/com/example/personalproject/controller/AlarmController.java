package com.example.personalproject.controller;

import com.example.personalproject.domain.dto.Response;
import com.example.personalproject.domain.response.UserAlarmResponse;
import com.example.personalproject.domain.response.UserCommentResponse;
import com.example.personalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AlarmController {

private final PostService postService;

    @GetMapping("/alarms")
    public Response<PageImpl<UserAlarmResponse>> alarm(@ApiIgnore Authentication authentication){
        String name = authentication.getName();
        PageRequest pageRequest = PageRequest.of(0,20, Sort.by("createdAt").descending());
        List<UserAlarmResponse> list = postService.alarm(name,pageRequest);
        return Response.success(new PageImpl<>(list));
    }

}
