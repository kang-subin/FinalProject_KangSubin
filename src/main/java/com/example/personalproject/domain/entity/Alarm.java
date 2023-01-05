package com.example.personalproject.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Alarm {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long formUserId;
    private Long targetId;
    private String text;
    private String alarmType;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

}
