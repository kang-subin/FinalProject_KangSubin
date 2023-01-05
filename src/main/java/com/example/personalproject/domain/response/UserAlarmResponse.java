package com.example.personalproject.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAlarmResponse {

private Long id;
private String alarmType;
private Long fromUserId;
private Long targetId;
private String text;
private LocalDateTime createdAt;

}
