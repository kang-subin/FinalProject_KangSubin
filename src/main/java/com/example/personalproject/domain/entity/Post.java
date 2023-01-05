package com.example.personalproject.domain.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@SQLDelete(sql = "UPDATE post SET deleted = true WHERE id = ?")
//@Where(clause = "deleted = false")
public class Post extends Date {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    //private boolean deleted = Boolean.FALSE;


}
