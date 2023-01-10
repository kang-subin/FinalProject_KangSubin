package com.example.personalproject.repository;

import com.example.personalproject.domain.entity.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm,Long> {

    Page<Alarm> findAllByUserId(Long id, PageRequest pageRequest);

}
