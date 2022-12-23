package com.example.personalproject.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter

public class Date {
    @CreatedDate
    @Column(updatable = false)
    private String createdAt;

    @LastModifiedDate
    @Column(updatable = false)
    private String lastModifiedAt;

    @PrePersist //해당 엔티티 저장하기 전
    void onPrePersist(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd hh:mm:ss"));
        this.lastModifiedAt = createdAt;
    }

    @PreUpdate //해당 엔티티 수정 하기 전
    void onPreUpdate(){
        this.lastModifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd hh:mm:ss"));
    }

}
